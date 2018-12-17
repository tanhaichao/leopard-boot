package io.leopard.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.JdbcUtils;

public class CountResultSetExtractor<K, V> implements ResultSetExtractor<Map<K, V>> {

	protected Class<V> keyClazz;

	private Class<V> valueClazz;

	public CountResultSetExtractor(Class<V> keyClazz, Class<V> valueClazz) {
		this.keyClazz = keyClazz;
		this.valueClazz = valueClazz;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<K, V> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<K, V> countMap = new LinkedHashMap<>();
		while (rs.next()) {
			K key = (K) JdbcUtils.getResultSetValue(rs, 1);
			V value = (V) this.getValue(rs);
			countMap.put(key, value);
		}
		return countMap;
	}

	protected Number getValue(ResultSet rs) throws SQLException {
		Object obj = rs.getObject(2);
		if (obj == null) {
			return null;
		}
		String number = obj.toString();
		if (Integer.class.equals(valueClazz)) {
			return Integer.parseInt(number);
		}
		else if (Long.class.equals(valueClazz)) {
			return Long.parseLong(number);
		}
		else if (Float.class.equals(valueClazz)) {
			return Float.parseFloat(number);
		}
		else if (Double.class.equals(valueClazz)) {
			return Double.parseDouble(number);
		}
		else {
			throw new RuntimeException("未知类型[" + valueClazz.getName() + "].");
		}
	}

}
