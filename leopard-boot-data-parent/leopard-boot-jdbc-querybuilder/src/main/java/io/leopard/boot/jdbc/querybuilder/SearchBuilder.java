package io.leopard.boot.jdbc.querybuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.StringUtils;

import io.leopard.boot.jdbc.SqlUtil;
import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.StatementParameter;
import io.leopard.jdbc.builder.Orderby;
import io.leopard.lang.Page;
import io.leopard.lang.Paging;
import io.leopard.lang.datatype.Sort;
import io.leopard.lang.datatype.TimeRange;
import io.leopard.lang.inum.Inum;
import io.leopard.lang.inum.Snum;

/**
 * 搜索条件构造器
 * 
 * @author 谭海潮
 *
 */
public abstract class SearchBuilder {

	/**
	 * 表别名
	 */
	protected String tableAlias = "";

	private String rangeStartFieldName;

	private String rangeEndFieldName;

	private TimeRange range;

	private String groupbyFieldName;

	private List<Orderby> orderList;

	private Integer limitStart;

	private Integer limitSize;

	private Map<String, Object> whereMap = new LinkedHashMap<String, Object>();

	private List<String> whereExpressionList = new ArrayList<String>();

	private Map<String, String> likeMap = new LinkedHashMap<String, String>();

	public SearchBuilder range(String fieldName, TimeRange range) {
		return this.range(fieldName, fieldName, range);
	}

	public SearchBuilder range(String startFieldName, String endFieldName, TimeRange range) {
		if (range == null) {
			return this;
		}
		this.rangeStartFieldName = startFieldName;
		this.rangeEndFieldName = endFieldName;
		this.range = range;
		return this;
	}

	public SearchBuilder groupby(String fieldName) {
		this.groupbyFieldName = fieldName;
		return this;
	}

	public SearchBuilder addWhere(String expression) {
		whereExpressionList.add(expression);
		return this;
	}

	public SearchBuilder addEnum(String fieldName, Inum inum) {
		if (inum != null) {
			this.addInt(fieldName, inum.getKey());
		}
		return this;
	}

	public SearchBuilder addEnums(String fieldName, Snum... snums) {
		if (snums.length == 0) {
			throw new IllegalArgumentException("枚举参数不能为空.");
		}

		StringBuilder sb = new StringBuilder();
		for (Snum snum : snums) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			// TODO 枚举key含有特殊符号时，未做过滤
			sb.append("'" + snum.getKey() + "'");
		}
		this.addWhere(fieldName + " in (" + sb.toString() + ") ");
		return this;
	}

	public SearchBuilder addEnum(String fieldName, Snum snum) {
		if (snum != null) {
			this.addString(fieldName, snum.getKey());
		}
		return this;
	}

	// TODO
	public SearchBuilder addSnumList(String fieldName, List<?> snumList) {
		if (snumList != null && !snumList.isEmpty()) {
			List<String> keyList = new ArrayList<String>();
			for (Object snum : snumList) {
				keyList.add(((Snum) snum).getKey());
			}
			return this.addStringList(fieldName, keyList);
		}
		return this;
	}

	public SearchBuilder addStringList(String fieldName, List<String> valueList) {
		if (valueList != null && !valueList.isEmpty()) {
			return this.addWhere(fieldName, valueList);
		}
		return this;
	}

	public SearchBuilder addString(String fieldName, String value) {
		return this.addString(fieldName, value, false);
	}

	public SearchBuilder addString(String fieldName, String value, boolean like) {
		if (StringUtils.hasLength(value)) {
			if (like) {
				this.addLike(fieldName, value);
			}
			else {
				this.addWhere(fieldName, value);
			}
		}
		return this;
	}

	public SearchBuilder addInt(String fieldName, int value) {
		if (value > 0) {
			this.addWhere(fieldName, value);
		}
		return this;
	}

	public SearchBuilder addBool(String fieldName, Boolean value) {
		if (value != null) {
			this.addWhere(fieldName, value);
		}
		return this;
	}

	public SearchBuilder addLong(String fieldName, long value) {
		if (value > 0) {
			this.addWhere(fieldName, value);
		}
		return this;
	}

	public SearchBuilder addWhere(String fieldName, Object value) {
		whereMap.put(fieldName, value);
		return this;
	}

	/**
	 * 位与运算
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SearchBuilder addWhereBitAnd(String fieldName, int value) {
		if (value > 0) {
			String expression = fieldName + "&" + value + "=" + value;
			this.addWhere(expression);
		}
		return this;
	}

	/**
	 * 位与运算
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SearchBuilder addWhereBitAnd(String fieldName, Inum inum) {
		if (inum != null) {
			int value = inum.getKey();
			// if (value <= 0) {
			// throw new RuntimeException("位与运算，枚举元素key必须大于0[" + value + "].");
			// }
			this.addWhereBitAnd(fieldName, value);
		}
		return this;
	}

	public SearchBuilder addMatch(String fieldName, String value) {
		if (StringUtils.isEmpty(value)) {
			// throw new IllegalArgumentException("参数不能为空.");
			return this;
		}
		{
			value = value.replace("*", "");
			value = value.replace("\"", "");
			value = value.replace("'", "");
			value = value.replace("+", "");
			value = value.replace("-", "");
			value = value.replace(">", "");
			value = value.replace("<", "");
			value = value.replace("(", "");
			value = value.replace(")", "");
			value = value.replace("~", "");
			value = value.replace("（", "");
			value = value.replace("）", "");
		}
		// TODO 是否需要过滤特殊字符？
		String searchx = SqlUtil.getIntString(value).trim();
		String expression = "MATCH(`" + fieldName + "`) AGAINST ('" + searchx + "' IN BOOLEAN MODE)";
		this.addWhere(expression);
		return this;
	}

	public SearchBuilder addLike(String fieldName, String value) {
		if (StringUtils.isEmpty(value)) {
			// throw new IllegalArgumentException("参数不能为空.");
			return this;
		}
		value = value.replace("%", "");
		if (StringUtils.isEmpty(value)) {
			throw new IllegalArgumentException("参数不能包含特殊字符[" + value + "].");
		}
		likeMap.put(fieldName, value);
		return this;
	}

	public SearchBuilder order(String fieldName) {
		return this.order(fieldName, "desc");
	}

	public SearchBuilder order(Snum snum) {
		if (snum == null) {
			return this;
		}
		return this.order(snum.getKey(), "desc");
	}

	public SearchBuilder order(String defaultFieldName, Sort sort) {
		return this.order(defaultFieldName, sort, "desc");
	}

	public SearchBuilder order(String defaultFieldName, Sort sort, String defaultOrderDirection) {
		String fieldName;
		if (sort == null || sort.getFieldName() == null) {
			fieldName = defaultFieldName;
		}
		else {
			fieldName = sort.getFieldName();
		}
		String orderDirection = defaultOrderDirection;
		if (sort != null) {
			if (sort.isDescending()) {
				orderDirection = "desc";
			}
			else if (sort.isAscending()) {
				orderDirection = "asc";
			}
		}
		return this.order(fieldName, orderDirection);
	}

	public SearchBuilder order(String fieldName, String orderDirection) {
		if (orderList == null) {
			orderList = new ArrayList<>();
		}
		Orderby orderby = new Orderby(fieldName, orderDirection);
		orderList.add(orderby);
		return this;
	}

	public SearchBuilder limit(int start, int size) {
		this.limitStart = start;
		this.limitSize = size;
		return this;
	}

	protected String getRangeSQL(StatementParameter param) {
		StringBuilder rangeSQL = new StringBuilder();
		if (this.range != null) {
			if (range.getStartTime() != null) {
				rangeSQL.append(this.tableAlias + this.rangeStartFieldName + ">=?");
				param.setDate(range.getStartTime());
			}

			if (range.getEndTime() != null) {
				if (rangeSQL.length() > 0) {
					rangeSQL.append(" and ");
				}
				rangeSQL.append(this.tableAlias + this.rangeEndFieldName + "<=?");
				param.setDate(range.getEndTime());
			}
		}

		return rangeSQL.toString();
	}

	protected String getWhereExpressionSQL() {
		if (this.whereExpressionList.isEmpty()) {
			return "";
		}
		StringBuilder whereSQL = new StringBuilder();
		for (String expression : this.whereExpressionList) {
			if (whereSQL.length() > 0) {
				whereSQL.append(" and ");
			}
			whereSQL.append(expression);
		}
		return whereSQL.toString();
	}

	protected String getWhereSQL(StatementParameter param) {
		StringBuilder whereSQL = new StringBuilder();
		for (Entry<String, Object> entry : this.whereMap.entrySet()) {
			String fieldName = entry.getKey();
			Object value = entry.getValue();
			if (whereSQL.length() > 0) {
				whereSQL.append(" and ");
			}
			if (value instanceof List) {
				@SuppressWarnings("rawtypes")
				List list = (List) value;
				String sql = this.getWhereInSql(fieldName, list);
				whereSQL.append(" ").append(sql);
			}
			else {
				whereSQL.append(columnName(fieldName)).append("=?");
				param.setObject(value.getClass(), value);
			}
		}
		return whereSQL.toString();
	}

	protected String getWhereInSql(String fieldName, @SuppressWarnings("rawtypes") List list) {
		if (list == null || list.isEmpty()) {
			throw new IllegalArgumentException("list参数不能为空.");
		}
		StringBuilder sql = new StringBuilder();
		sql.append(columnName(fieldName)).append(" in (");
		for (Object obj : list) {
			String str = (String) obj;
			str = escapeSQLParam(str);
			sql.append("'" + str + "',");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		return sql.toString();
	}

	protected String getLikeSQL(StatementParameter param) {
		StringBuilder whereSQL = new StringBuilder();
		for (Entry<String, String> entry : this.likeMap.entrySet()) {
			String fieldName = entry.getKey();
			String value = entry.getValue();
			if (whereSQL.length() > 0) {
				whereSQL.append(" and ");
			}
			whereSQL.append(this.tableAlias + columnName(fieldName)).append(" like '%" + escapeSQLParam(value) + "%'");
		}

		return whereSQL.toString();
	}

	protected String columnName(String fieldName) {
		return "`" + fieldName + "`";
	}

	/**
	 * 对SQL语句进行转义
	 * 
	 * @param param SQL语句
	 * @return 转义后的字符串
	 */
	private static String escapeSQLParam(final String param) {
		int stringLength = param.length();
		StringBuilder buf = new StringBuilder((int) (stringLength * 1.1));
		for (int i = 0; i < stringLength; ++i) {
			char c = param.charAt(i);
			switch (c) {
			case 0: /* Must be escaped for 'mysql' */
				buf.append('\\');
				buf.append('0');
				break;
			case '\n': /* Must be escaped for logs */
				buf.append('\\');
				buf.append('n');
				break;
			case '\r':
				buf.append('\\');
				buf.append('r');
				break;
			case '\\':
				buf.append('\\');
				buf.append('\\');
				break;
			case '\'':
				buf.append('\\');
				buf.append('\'');
				break;
			case '"': /* Better safe than sorry */
				buf.append('\\');
				buf.append('"');
				break;
			case '\032': /* This gives problems on Win32 */
				buf.append('\\');
				buf.append('Z');
				break;
			default:
				buf.append(c);
			}
		}
		return buf.toString();
	}

	public static class SQLInfo {
		StatementParameter param;

		private String sql;

		public StatementParameter getParam() {
			return param;
		}

		public void setParam(StatementParameter param) {
			this.param = param;
		}

		public String getSql() {
			return sql;
		}

		public void setSql(String sql) {
			this.sql = sql;
		}

	}

	protected String generateWhereSQL(StatementParameter param) {
		StringBuilder where = new StringBuilder();
		{
			String rangeSQL = this.getRangeSQL(param);
			if (rangeSQL.length() > 0) {
				where.append(rangeSQL);
			}
			{
				String whereSQL = this.getWhereSQL(param);
				if (whereSQL.length() > 0) {
					if (where.length() > 0) {
						where.append(" and ");
					}
					where.append(whereSQL);
				}
			}
			{
				String whereSQL = this.getWhereExpressionSQL();
				if (whereSQL.length() > 0) {
					if (where.length() > 0) {
						where.append(" and ");
					}
					where.append(whereSQL);
				}
			}
			{
				String whereSQL = this.getLikeSQL(param);
				if (whereSQL.length() > 0) {
					if (where.length() > 0) {
						where.append(" and ");
					}
					where.append(whereSQL);
				}
			}
		}
		if (where.length() > 0) {
			where.insert(0, " where ");
		}
		String whereSQL = where.toString();
		return whereSQL;
	}

	protected String generateOrderSQL(StatementParameter param) {
		StringBuilder sb = new StringBuilder();

		if (groupbyFieldName != null && groupbyFieldName.length() > 0) {
			sb.append(" group by " + this.tableAlias + groupbyFieldName);
		}

		// if (orderFieldName != null && orderFieldName.length() > 0) {
		if (orderList != null && !orderList.isEmpty()) {
			sb.append(" order by");
			int index = 0;
			for (Orderby orderby : orderList) {
				if (index > 0) {
					sb.append(",");
				}
				// sb.append(" order by " + orderby.getFieldName() + " " + orderby.getDirection());
				sb.append(" " + this.tableAlias + orderby.getFieldName() + " " + orderby.getDirection());
				index++;
			}
		}
		// }
		sb.append(" limit ?,?");
		param.setInt(limitStart);
		param.setInt(limitSize);
		String orderSql = sb.toString();
		return orderSql;
	}

	/**
	 * 生成搜索条件SQL
	 * 
	 * @param param
	 * @return
	 */
	protected String generateSearchSQL(StatementParameter param) {
		StringBuilder sql = new StringBuilder();
		String whereSQL = this.generateWhereSQL(param);
		sql.append(whereSQL);
		String orderSQL = generateOrderSQL(param);
		sql.append(orderSQL);
		return sql.toString();
	}

	protected abstract String generateSelectSQL(StatementParameter param);

	public <T> Paging<T> queryForPaging(Jdbc jdbc, Class<T> elementType) {
		StatementParameter param = new StatementParameter();
		String sql = generateSelectSQL(param);
		// System.err.println("sql:" + sqlInfo.getSql());
		return jdbc.queryForPaging(sql, elementType, param);
	}

	public <T> Paging<T> queryForPaging(Jdbc jdbc, Class<T> elementType, int start, int size) {
		this.limit(start, size);
		return this.queryForPaging(jdbc, elementType);
	}

	public <T> Page<T> queryForPage(Jdbc jdbc, Class<T> elementType) {
		StatementParameter param = new StatementParameter();
		String sql = generateSelectSQL(param);
		System.err.println("queryForPage sql:" + sql);
		return jdbc.queryForPage(sql, elementType, param);
	}

	public <T> Page<T> queryForPage(Jdbc jdbc, Class<T> elementType, int start, int size) {
		this.limit(start, size);
		return this.queryForPage(jdbc, elementType);
	}

	public <T> List<T> queryForList(Jdbc jdbc, Class<T> elementType) {
		StatementParameter param = new StatementParameter();
		String sql = generateSelectSQL(param);
		// System.err.println("sql:" + sql);
		return jdbc.queryForList(sql, elementType, param);
	}

	public <T> List<T> queryForList(Jdbc jdbc, Class<T> elementType, int start, int size) {
		this.limit(start, size);
		return this.queryForList(jdbc, elementType);
	}

}
