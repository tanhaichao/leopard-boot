package io.leopard.jdbc.builder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.StringUtils;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.StatementParameter;
import io.leopard.lang.Page;
import io.leopard.lang.Paging;
import io.leopard.lang.datatype.Sort;
import io.leopard.lang.datatype.TimeRange;
import io.leopard.lang.inum.Inum;
import io.leopard.lang.inum.Snum;

public class QueryBuilder {

	private String tableName;

	private String rangeStartFieldName;

	private String rangeEndFieldName;

	private TimeRange range;

	private List<Orderby> orderList;

	private String groupbyFieldName;

	private Integer limitStart;

	private Integer limitSize;

	private Map<String, Object> whereMap = new LinkedHashMap<String, Object>();

	private List<String> whereExpressionList = new ArrayList<String>();

	private Map<String, String> likeMap = new LinkedHashMap<String, String>();

	public QueryBuilder(String tableName) {
		this.tableName = tableName;
	}

	public QueryBuilder range(String fieldName, TimeRange range) {
		return this.range(fieldName, fieldName, range);
	}

	public QueryBuilder range(String startFieldName, String endFieldName, TimeRange range) {
		if (range == null) {
			return this;
		}
		this.rangeStartFieldName = startFieldName;
		this.rangeEndFieldName = endFieldName;
		this.range = range;
		return this;
	}

	public QueryBuilder addWhere(String expression) {
		whereExpressionList.add(expression);
		return this;
	}

	public QueryBuilder addEnum(String fieldName, Inum inum) {
		if (inum != null) {
			this.addInt(fieldName, inum.getKey());
		}
		return this;
	}

	public QueryBuilder addEnums(String fieldName, Snum... snums) {
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

	public QueryBuilder addEnum(String fieldName, Snum snum) {
		if (snum != null) {
			this.addString(fieldName, snum.getKey());
		}
		return this;
	}

	// TODO
	public QueryBuilder addSnumList(String fieldName, List<?> snumList) {
		if (snumList != null && !snumList.isEmpty()) {
			List<String> keyList = new ArrayList<String>();
			for (Object snum : snumList) {
				keyList.add(((Snum) snum).getKey());
			}
			return this.addStringList(fieldName, keyList);
		}
		return this;
	}

	public QueryBuilder addStringList(String fieldName, List<String> valueList) {
		if (valueList != null && !valueList.isEmpty()) {
			return this.addWhere(fieldName, valueList);
		}
		return this;
	}

	public QueryBuilder addString(String fieldName, String value) {
		return this.addString(fieldName, value, false);
	}

	public QueryBuilder addString(String fieldName, String value, boolean like) {
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

	public QueryBuilder addInt(String fieldName, int value) {
		if (value > 0) {
			this.addWhere(fieldName, value);
		}
		return this;
	}

	public QueryBuilder addBool(String fieldName, Boolean value) {
		if (value != null) {
			this.addWhere(fieldName, value);
		}
		return this;
	}

	public QueryBuilder addLong(String fieldName, long value) {
		if (value > 0) {
			this.addWhere(fieldName, value);
		}
		return this;
	}

	public QueryBuilder addWhere(String fieldName, Object value) {
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
	public QueryBuilder addWhereBitAnd(String fieldName, int value) {
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
	public QueryBuilder addWhereBitAnd(String fieldName, Inum inum) {
		if (inum != null) {
			int value = inum.getKey();
			// if (value <= 0) {
			// throw new RuntimeException("位与运算，枚举元素key必须大于0[" + value + "].");
			// }
			this.addWhereBitAnd(fieldName, value);
		}
		return this;
	}

	public QueryBuilder addLike(String fieldName, String value) {
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

	public QueryBuilder order(String fieldName) {
		return this.order(fieldName, "desc");
	}

	public QueryBuilder order(Snum snum) {
		if (snum == null) {
			return this;
		}
		return this.order(snum.getKey(), "desc");
	}

	public QueryBuilder order(String defaultFieldName, Sort sort) {
		return this.order(defaultFieldName, sort, "desc");
	}

	public QueryBuilder order(String defaultFieldName, Sort sort, String defaultOrderDirection) {
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
				return this.order(fieldName, "desc");
			}
			else if (sort.isAscending()) {
				return this.order(fieldName, "asc");
			}
		}
		return this.order(fieldName, orderDirection);
	}

	public QueryBuilder order(String fieldName, String orderDirection) {
		if (orderList == null) {
			orderList = new ArrayList<>();
		}
		Orderby orderby = new Orderby(fieldName, orderDirection);
		orderList.add(orderby);
		return this;
	}

	public QueryBuilder groupby(String fieldName) {
		this.groupbyFieldName = fieldName;
		return this;
	}

	public QueryBuilder limit(int start, int size) {
		this.limitStart = start;
		this.limitSize = size;
		return this;
	}

	protected String getRangeSQL(StatementParameter param) {
		StringBuilder rangeSQL = new StringBuilder();
		if (this.range != null) {
			if (range.getStartTime() != null) {
				rangeSQL.append(this.rangeStartFieldName + ">=?");
				param.setDate(range.getStartTime());
			}

			if (range.getEndTime() != null) {
				if (rangeSQL.length() > 0) {
					rangeSQL.append(" and ");
				}
				rangeSQL.append(this.rangeEndFieldName + "<=?");
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
				whereSQL.append("`" + fieldName + "`").append("=?");
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
		sql.append("`" + fieldName + "`").append(" in (");
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
			whereSQL.append("`" + fieldName + "`").append(" like '%" + escapeSQLParam(value) + "%'");
		}

		return whereSQL.toString();
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

	protected SQLInfo toSqlInfo() {
		return this.toSqlInfo("*", false);
	}

	protected SQLInfo toSqlInfo(String select, boolean counting) {
		StatementParameter param = new StatementParameter();
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		sb.append(select);
		sb.append(" from `" + tableName + "`");
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
			sb.append(" where " + where.toString());
		}

		if (!counting) {
			// System.err.println("groupbyFieldName:" + groupbyFieldName + " orderFieldName:" + orderFieldName);
			if (groupbyFieldName != null && groupbyFieldName.length() > 0) {
				sb.append(" group by " + groupbyFieldName);
			}
			// if (orderFieldName != null && orderFieldName.length() > 0) {
			if (orderList != null && !orderList.isEmpty()) {
				sb.append(" order by");
				int index = 0;
				for (Orderby orderby : orderList) {
					if (index > 0) {
						sb.append(",");
					}
					sb.append(" " + orderby.getFieldName() + " " + orderby.getDirection());
					index++;
				}
			}
			sb.append(" limit ?,?");
			param.setInt(limitStart);
			param.setInt(limitSize);
		}

		String sql = sb.toString();
		SQLInfo sqlInfo = new SQLInfo();
		sqlInfo.setParam(param);
		sqlInfo.setSql(sql);
		return sqlInfo;
	}

	public <T> Paging<T> queryForPaging(Jdbc jdbc, Class<T> elementType) {
		SQLInfo sqlInfo = this.toSqlInfo();
		// System.err.println("sql:" + sqlInfo.getSql());
		return jdbc.queryForPaging(sqlInfo.getSql(), elementType, sqlInfo.getParam());
	}

	public <T> Paging<T> queryForPaging(Jdbc jdbc, Class<T> elementType, int start, int size) {
		this.limit(start, size);
		return this.queryForPaging(jdbc, elementType);
	}

	public <T> Page<T> queryForPage(Jdbc jdbc, Class<T> elementType) {
		SQLInfo sqlInfo = this.toSqlInfo();
		// System.err.println("sql:" + sqlInfo.getSql());
		return jdbc.queryForPage(sqlInfo.getSql(), elementType, sqlInfo.getParam());
	}

	public <T> Page<T> queryForPage(Jdbc jdbc, Class<T> elementType, int start, int size) {
		this.limit(start, size);
		return this.queryForPage(jdbc, elementType);
	}

	public <T> List<T> queryForList(Jdbc jdbc, Class<T> elementType) {
		SQLInfo sqlInfo = this.toSqlInfo();
		// System.err.println("sql:" + sql);
		return jdbc.queryForList(sqlInfo.getSql(), elementType, sqlInfo.getParam());
	}

	public <T> List<T> queryForList(Jdbc jdbc, Class<T> elementType, int start, int size) {
		this.limit(start, size);
		return this.queryForList(jdbc, elementType);
	}

	public int count(Jdbc jdbc) {
		SQLInfo sqlInfo = this.toSqlInfo("count(*)", true);
		return jdbc.queryForInt(sqlInfo.getSql(), sqlInfo.getParam());
	}

	public Double sumForDouble(Jdbc jdbc, String columnName) {
		SQLInfo sqlInfo = this.toSqlInfo("sum(`" + columnName + "`)", true);
		return jdbc.queryForDouble(sqlInfo.getSql(), sqlInfo.getParam());
	}
}
