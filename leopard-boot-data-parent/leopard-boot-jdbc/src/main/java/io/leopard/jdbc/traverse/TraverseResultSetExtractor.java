package io.leopard.jdbc.traverse;

import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import io.leopard.jdbc.LeopardBeanPropertyRowMapper;

/**
 * 遍历记录
 * 
 * @author 谭海潮
 *
 * @param <T>
 */
public class TraverseResultSetExtractor<T> implements ResultSetExtractor<Integer> {

	private final RowMapper<T> rowMapper;

	private final Traverser<T> traverser;

	private Class<T> mappedClass;

	@SuppressWarnings("unchecked")
	public TraverseResultSetExtractor(Traverser<T> traverser) {
		// this.mappedClass ;
		ParameterizedType type = (ParameterizedType) traverser.getClass().getGenericInterfaces()[0];
		this.mappedClass = (Class<T>) type.getActualTypeArguments()[0];
		// System.out.println("name:" + mappedClass.getName());
		this.rowMapper = new LeopardBeanPropertyRowMapper<T>(mappedClass);
		this.traverser = traverser;
	}

	@Override
	public Integer extractData(ResultSet rs) throws SQLException {
		int rowCount = 0;
		while (rs.next()) {
			T bean = this.rowMapper.mapRow(rs, rowCount++);
			traverser.traverse(bean);
		}
		return rowCount;
	}

}
