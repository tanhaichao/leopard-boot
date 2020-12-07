package io.leopard.boot.jdbc;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import io.leopard.jdbc.InvalidParamDataAccessException;
import io.leopard.jdbc.StatementParameter;
import io.leopard.lang.datatype.Month;
import io.leopard.lang.datatype.OnlyDate;
import io.leopard.lang.inum.Snum;

/**
 * SQL工具类
 * 
 * @author 谭海潮
 *
 */
public class SqlUtil {

	/**
	 * 用于MySQL全文检索中文编码.
	 * 
	 * @param strings
	 * @return
	 */
	public static String getSearchx(String... strings) {
		StringBuilder sb = new StringBuilder();
		for (String string : strings) {
			if (StringUtils.hasLength(string)) {
				if (sb.length() > 0) {
					sb.append(" ");
				}
				sb.append(string);
			}
		}
		return getIntString(sb.toString());
	}

	/**
	 * 用于MySQL全文检索中文编码.
	 * <p>
	 * 
	 * @param str
	 * @return
	 */
	public static String getIntString(final String str) {
		if (str == null) {
			return "";
		}
		byte[] bytes;
		try {
			bytes = str.toLowerCase().getBytes("GBK");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		StringBuilder sb = new StringBuilder();
		int iscn = 0;
		for (int i = 0; i < bytes.length; i++) {
			int j = bytes[i];
			if (bytes[i] < 0) {
				j = j * (-1);
				if (j < 10) {
					sb.append('0');
				}
				sb.append(j);
				iscn++;
				if (iscn == 2) {
					sb.append(' ');
					iscn = 0;
				}
			}
			else {
				byte[] b = new byte[] { bytes[i] };
				for (int n = 0; n < b.length; n++) {
					String str1 = "000" + b[n];
					str1 = str1.substring(str1.length() - 4);
					sb.append(str1).append(' ');
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 获取拼接参数后的sql.
	 * 
	 * @param sql sql
	 * @param param 参数列表
	 * @return 拼接后的sql
	 */
	public static String getSQL(String sql, StatementParameter param) {
		int i = 0;
		while (sql.indexOf('?') > -1) {
			if (param == null) {
				throw new InvalidParamDataAccessException("没有设置参数.");
			}
			if (i >= param.size()) {
				return sql;
			}
			Class<?> type = param.getType(i);
			Object obj = param.getObject(i);
			String value = getValue(type, obj);
			sql = sql.substring(0, sql.indexOf('?')) + value + sql.substring(sql.indexOf('?') + 1, sql.length());
			i++;
		}
		return sql;//
	}

	protected static String getValue(Class<?> type, Object obj) {
		String value;
		if (type.equals(String.class)) {
			value = "'" + ((String) obj) + "'";
		}

		else if (type.equals(Date.class)) {
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) obj);
			value = "'" + time + "'";
		}
		else if (type.equals(OnlyDate.class)) {
			value = "'" + ((OnlyDate) obj).toString() + "'";
		}
		else if (type.equals(Month.class)) {
			value = "'" + ((Month) obj).toString() + "'";
		}
		else if (type.equals(Integer.class)) {
			value = Integer.toString(((Integer) obj));
		}
		else if (type.equals(Boolean.class)) {
			value = (((Boolean) obj) ? 1 : 0) + "";
		}
		else if (type.equals(Float.class)) {
			value = Float.toString(((Float) obj));
		}
		else if (type.equals(Double.class)) {
			value = Double.toString(((Double) obj));
		}
		else if (type.equals(Long.class)) {
			value = Long.toString(((Long) obj));
		}
		else if (type.equals(byte[].class)) {
			value = "byte[]";
		}
		else {
			throw new InvalidParamDataAccessException("未知参数类型[" + type.getName() + "].");
		}
		return value;
	}

	/**
	 * 转换成select count(*)语句.
	 * 
	 * @param sql
	 * @return
	 */
	public static String toCountSql(String sql) {
		sql = sql.replaceAll("select .*? from", "select count(*) from");
		sql = sql.replaceAll("SELECT .*? FROM", "SELECT count(*) FROM");
		sql = sql.replaceAll(" LIMIT.*", "");
		sql = sql.replaceAll(" limit.*", "");
		return sql;
	}

	/**
	 * 对SQL语句进行转义
	 * 
	 * @param param SQL语句
	 * @return 转义后的字符串
	 */
	public static String escapeSQLParam(final String param) {
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

	public static String toInBySnum(List<Snum> list) {
		StringBuilder sb = new StringBuilder();
		for (Snum snum : list) {
			String key = snum.getKey();
			key = escapeSQLParam(key);
			sb.append("'" + key + "',");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static String toIn(List<String> list) {
		StringBuilder sb = new StringBuilder();
		for (String str : list) {
			str = escapeSQLParam(str);
			sb.append("'" + str + "',");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static String toInByLong(List<Long> list) {
		StringBuilder sb = new StringBuilder();
		int index = 0;
		for (Long number : list) {
			if (index > 0) {
				sb.append(",");
			}
			sb.append(number);
			index++;
		}
		return sb.toString();
	}
}
