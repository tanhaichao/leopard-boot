package io.leopard.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.JdbcUtils;

public class CountResultSetExtractor<K, V> implements ResultSetExtractor<Map<K, V>> {

	@Override
	public Map<K, V> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<K, V> countMap = new LinkedHashMap<>();
		while (rs.next()) {
			@SuppressWarnings("unchecked")
			K key = (K) JdbcUtils.getResultSetValue(rs, 1);
			@SuppressWarnings("unchecked")
			V value = (V) JdbcUtils.getResultSetValue(rs, 2);
			countMap.put(key, value);
		}
		return countMap;
	}

}
