package io.leopard.jdbc;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import io.leopard.boot.jdbc.SqlUtil;
import io.leopard.jdbc.builder.AbstractSqlBuilder;
import io.leopard.jdbc.builder.InsertBuilder;
import io.leopard.jdbc.builder.NullInsertBuilder;
import io.leopard.jdbc.builder.NullReplaceBuilder;
import io.leopard.jdbc.builder.NullUpdateBuilder;
import io.leopard.jdbc.builder.ReplaceBuilder;
import io.leopard.jdbc.builder.SqlBuilder;
import io.leopard.jdbc.builder.UpdateBuilder;
import io.leopard.jdbc.datasource.JdbcDataSource;
import io.leopard.jdbc.logger.JdbcLogger;
import io.leopard.jdbc.logger.JdbcLoggerImpl;
import io.leopard.jdbc.mapper.CountResultSetExtractor;
import io.leopard.json.Json;
import io.leopard.lang.Page;
import io.leopard.lang.PageImpl;
import io.leopard.lang.Paging;
import io.leopard.lang.PagingImpl;
import io.leopard.lang.datatype.Month;
import io.leopard.lang.datatype.OnlyDate;
import io.leopard.lang.inum.Bnum;
import io.leopard.lang.inum.Inum;
import io.leopard.lang.inum.Onum;
import io.leopard.lang.inum.Snum;
import io.leopard.lang.inum.dynamic.DynamicEnum;

/**
 * Jdbc接口MySQL实现.
 * 
 * @author 阿海
 * 
 */
public class JdbcMysqlImpl implements Jdbc {

	protected JdbcLogger jdbcLogger = JdbcLoggerImpl.getInstance();

	protected JdbcTemplate jdbcTemplate;

	protected DataSource dataSource;

	/**
	 * JDBC名称
	 */
	private String name;

	public void setDataSource(DataSource dataSource) {
		// System.err.println("dataSource:" + dataSource.getClass().getName());
		this.dataSource = dataSource;
		if (this.jdbcTemplate == null || dataSource != this.jdbcTemplate.getDataSource()) {
			this.jdbcTemplate = new JdbcTemplate(dataSource);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public DataSource getDataSource() {
		return jdbcTemplate.getDataSource();
	}

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public String printSQL(Log logger, String sql, StatementParameter param) {
		String sql1 = this.getSQL(sql, param);
		logger.info(sql1);
		return sql1;
	}

	@Override
	public String getSQL(String sql, StatementParameter param) {
		return SqlUtil.getSQL(sql, param);
	}

	@Override
	public int[] batchUpdate(String[] sqls) {
		return this.getJdbcTemplate().batchUpdate(sqls);
	}

	@Override
	public int[] batchUpdate(String sql, BatchPreparedStatementSetter setter) {
		return this.getJdbcTemplate().batchUpdate(sql, setter);
	}

	@Override
	public <T> T query(String sql, Class<T> elementType) {
		try {
			return this.getJdbcTemplate().queryForObject(sql, new LeopardBeanPropertyRowMapper<T>(elementType));
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public <T> T query(String sql, Class<T> elementType, Object... params) {
		return this.query(sql, elementType, toStatementParameter(sql, params));
	}

	@Override
	public <T> T query(String sql, Class<T> elementType, StatementParameter param) {
		try {
			return this.getJdbcTemplate().queryForObject(sql, param.getArgs(), new LeopardBeanPropertyRowMapper<T>(elementType));
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> queryForMaps(String sql) {
		try {
			List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);
			return list;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> queryForMaps(String sql, Object... params) {
		try {
			List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql, params);
			return list;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	protected String appendLimitSql(String sql, int start, int size) {
		if (sql.endsWith(";")) {
			sql = sql.substring(0, sql.length() - 1);
		}
		return sql + " LIMIT " + start + "," + size + ";";
	}

	@Override

	public <T> List<T> queryForList(String sql, Class<T> elementType) {
		try {
			List<T> list = this.getJdbcTemplate().query(sql, new LeopardBeanPropertyRowMapper<T>(elementType));
			return list;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<Long> queryForLongs(String sql, StatementParameter param, int start, int size) {
		sql = this.appendLimitSql(sql, start, size);
		return this.queryForLongs(sql, param);
	}

	@Override
	public List<Long> queryForLongs(String sql, Object... params) {
		return this.queryForLongs(sql, this.toStatementParameter(sql, params));
	}

	@Override
	public List<Long> queryForLongs(String sql, StatementParameter param) {
		List<Long> list = jdbcTemplate.query(sql, param.getArgs(), new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int index) {
				try {
					return rs.getLong(1);
				}
				catch (SQLException e) {
					throw new InvalidParamDataAccessException(e);
				}
			}
		});
		return list;
	}

	@Override
	public List<Integer> queryForInts(String sql, StatementParameter param) {
		List<Integer> list = jdbcTemplate.query(sql, param.getArgs(), new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet rs, int index) {
				try {
					return rs.getInt(1);
				}
				catch (SQLException e) {
					throw new InvalidParamDataAccessException(e);
				}
			}
		});
		return list;
	}

	@Override
	public List<String> queryForStrings(String sql) {
		List<String> list = jdbcTemplate.query(sql, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int index) {
				try {
					return rs.getString(1);
				}
				catch (SQLException e) {
					throw new InvalidParamDataAccessException(e);
				}
			}
		});
		return list;
	}

	@Override
	public List<String> queryForStrings(String sql, Object... params) {
		return this.queryForStrings(sql, toStatementParameter(sql, params));
	}

	@Override
	public List<String> queryForStrings(String sql, StatementParameter param) {
		List<String> list = jdbcTemplate.query(sql, param.getArgs(), new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int index) {
				try {
					return rs.getString(1);
				}
				catch (SQLException e) {
					throw new InvalidParamDataAccessException(e);
				}
			}
		});
		return list;
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> elementType, StatementParameter param, int start, int size) {
		sql = this.appendLimitSql(sql, start, size);
		return this.queryForList(sql, elementType, param);
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> elementType, Object... params) {
		return this.queryForList(sql, elementType, toStatementParameter(sql, params));
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> elementType, StatementParameter param) {
		try {
			List<T> list = this.getJdbcTemplate().query(sql, param.getArgs(), new LeopardBeanPropertyRowMapper<T>(elementType));
			return list;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long queryForLong(String sql) {
		try {
			Number number = this.getJdbcTemplate().queryForObject(sql, Long.class);
			return (number != null ? number.longValue() : 0);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Long queryForLong(String sql, StatementParameter param) {
		Object[] args = param.getArgs();
		int[] argTypes = param.getArgTypes();
		try {
			Number number = this.getJdbcTemplate().queryForObject(sql, args, argTypes, Long.class);
			return (number != null ? number.longValue() : 0);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Integer queryForInt(String sql) {
		try {
			Number number = this.getJdbcTemplate().queryForObject(sql, Integer.class);
			return (number != null ? number.intValue() : 0);
			//
			// int result = this.getJdbcTemplate().queryForInt(sql);
			// return result;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public boolean exist(String sql) {
		return this.queryForInt(sql) > 0;
	}

	@Override
	public boolean exist(String sql, StatementParameter param) {
		return this.queryForInt(sql, param) > 0;
	}

	@Override
	public Integer queryForInt(String sql, StatementParameter param) {
		Object[] args = param.getArgs();
		int[] argTypes = param.getArgTypes();
		try {
			Number number = this.getJdbcTemplate().queryForObject(sql, args, argTypes, Integer.class);
			return (number != null ? number.intValue() : 0);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Float queryForFloat(String sql, StatementParameter param) {
		Object[] args = param.getArgs();
		int[] argTypes = param.getArgTypes();
		try {
			Number number = this.getJdbcTemplate().queryForObject(sql, args, argTypes, Float.class);
			return (number != null ? number.floatValue() : 0);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Double queryForDouble(String sql, Object... params) {
		return this.queryForDouble(sql, this.toStatementParameter(sql, params));
	}

	@Override
	public Double queryForDouble(String sql, StatementParameter param) {
		Object[] args = param.getArgs();
		int[] argTypes = param.getArgTypes();
		try {
			Number number = this.getJdbcTemplate().queryForObject(sql, args, argTypes, Double.class);
			return (number != null ? number.doubleValue() : 0);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public java.util.Date queryForDate(String sql) {
		try {
			java.util.Date result = this.getJdbcTemplate().queryForObject(sql, java.util.Date.class);
			return result;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public java.util.Date queryForDate(String sql, StatementParameter param) {
		Object[] args = param.getArgs();
		int[] argTypes = param.getArgTypes();
		try {
			java.util.Date result = this.getJdbcTemplate().queryForObject(sql, args, argTypes, java.util.Date.class);
			return result;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public String queryForString(String sql) {
		try {
			String result = this.getJdbcTemplate().queryForObject(sql, String.class);

			return result;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public String queryForString(String sql, Object... params) {
		return this.queryForString(sql, this.toStatementParameter(sql, params));
	}

	@Override
	public String queryForString(String sql, StatementParameter param) {
		Object[] args = param.getArgs();
		int[] argTypes = param.getArgTypes();
		try {
			String result = this.getJdbcTemplate().queryForObject(sql, args, argTypes, String.class);
			return result;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public boolean insertIgnoreForBoolean(String sql, StatementParameter param) {
		try {
			return this.insertForBoolean(sql, param);
		}
		catch (DuplicateKeyException e) {
			return false;
		}
	}

	@Override
	public long insertForLastId(final String sql, final StatementParameter param) {
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		this.getJdbcTemplate().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				param.setValues(pstmt);
				return pstmt;
			}
		}, keyHolder);
		return keyHolder.getKey().longValue();
	}

	@Override
	public boolean insertForBoolean(String sql, StatementParameter param) {
		return this.updateForBoolean(sql, param);
	}

	@Override
	public boolean updateForBoolean(String sql, StatementParameter param) {
		// System.out.println("updateForBoolean sql:" + sql);
		int updatedCount = this.update(sql, param);
		return (updatedCount > 0);
	}

	/**
	 * 将参数列表转成StatementParameter.
	 * 
	 * @param params 参数列表
	 * @return 转换后的StatementParameter
	 */
	@SuppressWarnings("rawtypes")
	protected StatementParameter toStatementParameter(String sql, Object... params) {
		StatementParameter param = new StatementParameter();
		for (Object p : params) {
			if (p instanceof Integer) {
				param.setInt((Integer) p);
			}
			else if (p instanceof Long) {
				param.setLong((Long) p);
			}
			else if (p instanceof Boolean) {
				param.setBool((Boolean) p);
			}
			else if (p instanceof Float) {
				param.setFloat((Float) p);
			}
			else if (p instanceof Double) {
				param.setDouble((Double) p);
			}
			else if (p instanceof String) {
				param.setString((String) p);
			}
			else if (p instanceof Date) {
				param.setDate((Date) p);
			}
			else if (p instanceof List) {
				param.setList((List) p);
			}
			else if (p instanceof Onum) {
				param.setEnum((Onum) p);
			}
			// 自定义数据类型start
			else if (p instanceof OnlyDate) {
				param.setDate(((OnlyDate) p));
			}
			else if (p instanceof Month) {
				param.setString(((Month) p).toString());
			}
			else {
				param.setObject(p);
				// throw new IllegalArgumentException("未知数据类型[" + p.getClass().getName() + "].");
			}
		}
		return param;
	}

	@Override
	public boolean updateForBoolean(String sql, Object... params) {
		return this.updateForBoolean(sql, toStatementParameter(sql, params));
	}

	@Override
	public int update(String sql, StatementParameter param) {
		int updatedCount = this.getJdbcTemplate().update(sql, param.getParameters());
		jdbcLogger.update(updatedCount, sql, param.getArgs());
		return updatedCount;
	}

	@Override
	public int update(String sql) {
		return this.getJdbcTemplate().update(sql);
	}

	@Override
	public boolean insertIgnoreForBoolean(InsertBuilder builder) {
		return this.insertIgnoreForBoolean(builder.getSql(), builder.getParam());
	}

	@Override
	public boolean insertIgnoreForBoolean(ReplaceBuilder builder) {
		return this.insertIgnoreForBoolean(builder.getSql(), builder.getParam());
	}

	@Override
	public boolean insertForBoolean(InsertBuilder builder) {
		return this.insertForBoolean(builder.getSql(), builder.getParam());
	}

	@Override
	public boolean insertForBoolean(ReplaceBuilder builder) {
		return this.insertForBoolean(builder.getSql(), builder.getParam());
	}

	@Override
	public boolean updateForBoolean(SqlBuilder builder) {
		return this.updateForBoolean(builder.getSql(), builder.getParam());
	}

	@Override
	public int update(SqlBuilder builder) {
		return this.update(builder.getSql(), builder.getParam());
	}

	@Override
	public int update(String sql, Object... params) {
		return this.update(sql, toStatementParameter(sql, params));
	}

	@Override
	public Long incr(String sql, StatementParameter param) {
		boolean success = this.updateForBoolean(sql, param);
		if (success) {
			return 1L;
		}
		else {
			return 0L;
		}
	}

	@Override
	public List<Integer> queryForInts(String sql, StatementParameter param, int start, int size) {
		sql = this.appendLimitSql(sql, start, size);
		return this.queryForInts(sql, param);
	}

	@Override
	public List<String> queryForStrings(String sql, int start, int size) {
		sql = this.appendLimitSql(sql, start, size);
		return this.queryForStrings(sql);
	}

	@Override
	public List<String> queryForStrings(String sql, StatementParameter param, int start, int size) {
		sql = this.appendLimitSql(sql, start, size);
		return this.queryForStrings(sql, param);
	}

	@Override
	public Long queryForLong(String sql, Object... params) {
		return this.queryForLong(sql, this.toStatementParameter(sql, params));
	}

	@Override
	public Integer queryForInt(String sql, Object... params) {
		return this.queryForInt(sql, this.toStatementParameter(sql, params));
	}

	@Override
	public boolean insertForBoolean(String sql, Object... params) {
		return this.insertForBoolean(sql, toStatementParameter(sql, params));
	}

	// @Override
	// public boolean insertByBean(String sql, Object bean) {
	// return this.insertForBoolean(sql, SqlParserUtil.toInsertParameter(sql, bean));
	// }

	@Override
	public boolean insert(String tableName, Object bean) {
		InsertBuilder builder = new NullInsertBuilder(tableName);

		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			Class<?> type = field.getType();
			field.setAccessible(true);
			Object obj;
			try {
				obj = field.get(bean);
			}
			// catch (IllegalArgumentException e) {
			// throw new InvalidDataAccessApiUsageException(e.getMessage());
			// }
			catch (IllegalAccessException e) {
				throw new InvalidDataAccessApiUsageException(e.getMessage());
			}
			this.setFieldValue(builder, type, fieldName, obj);
		}
		return this.insertForBoolean(builder);
	}

	@Override
	public boolean replace(String tableName, Object bean) {
		ReplaceBuilder builder = new NullReplaceBuilder(tableName);

		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			Class<?> type = field.getType();
			field.setAccessible(true);
			Object obj;
			try {
				obj = field.get(bean);
			}
			// catch (IllegalArgumentException e) {
			// throw new InvalidDataAccessApiUsageException(e.getMessage());
			// }
			catch (IllegalAccessException e) {
				throw new InvalidDataAccessApiUsageException(e.getMessage());
			}
			this.setFieldValue(builder, type, fieldName, obj);
		}
		return this.insertForBoolean(builder);
	}
	// @Override
	// public boolean updateByBean(String sql, Object bean) {
	// return this.updateForBoolean(sql, SqlParserUtil.toUpdateParameter(sql, bean));
	// }

	@Override
	public <T> Paging<T> queryForPaging(String sql, Class<T> elementType) {
		List<T> list = this.queryForList(sql, elementType);
		String countSql = SqlUtil.toCountSql(sql);
		int totalCount = this.queryForInt(countSql);

		PagingImpl<T> paging = new PagingImpl<T>();
		paging.setTotalCount(totalCount);
		paging.setList(list);
		return paging;
	}

	@Override
	public <T> Paging<T> queryForPaging(String sql, Class<T> elementType, Object... params) {
		StatementParameter param = toStatementParameter(sql, params);
		List<T> list = this.queryForList(sql, elementType, param);
		CountSqlParser parser = new CountSqlParser(sql, param);

		// System.err.println("countSQL:" + countSqlParser.getCountSql());
		int totalCount = this.queryForInt(parser.getCountSql(), parser.getCountParam());

		PagingImpl<T> paging = new PagingImpl<T>();
		paging.setTotalCount(totalCount);
		paging.setList(list);

		if (parser.getSize() != null) {
			paging.setNextPage(PageImpl.hasNextPage(totalCount, parser.getStart(), parser.getSize()));
			paging.setPageSize(parser.getSize());
		}
		return paging;
	}

	@Override
	public <T> Paging<T> queryForPaging(String sql, Class<T> elementType, StatementParameter param) {
		List<T> list = this.queryForList(sql, elementType, param);
		CountSqlParser parser = new GroupCountSqlParser(sql, param);
		String countSql = parser.getCountSql();
		// System.err.println("countSql:" + countSql);
		int totalCount = this.queryForInt(countSql, parser.getCountParam());
		// System.err.println("countSql:" + countSql + " totalCount:" + totalCount);

		PagingImpl<T> paging = new PagingImpl<T>();
		paging.setTotalCount(totalCount);
		paging.setList(list);
		if (parser.getSize() != null) {
			paging.setNextPage(PageImpl.hasNextPage(totalCount, parser.getStart(), parser.getSize()));
			paging.setPageSize(parser.getSize());
		}
		return paging;
	}

	@Override
	public <T> Paging<T> queryForPaging(String sql, Class<T> elementType, StatementParameter param, int start, int size) {
		PageableRowMapperResultSetExtractor<T> extractor = new PageableRowMapperResultSetExtractor<T>(new LeopardBeanPropertyRowMapper<T>(elementType), start, size);
		List<T> list = this.getJdbcTemplate().query(sql, param.getArgs(), extractor);
		int totalCount = extractor.getCount();
		PagingImpl<T> paging = new PagingImpl<T>();
		paging.setTotalCount(totalCount);
		paging.setList(list);

		paging.setNextPage(PageImpl.hasNextPage(totalCount, start, size));
		paging.setPageSize(size);

		return paging;
	}

	@Override
	public <T> Page<T> queryForPage(String sql, Class<T> elementType) {
		List<T> list = this.queryForList(sql, elementType);
		String countSql = SqlUtil.toCountSql(sql);
		int totalCount = this.queryForInt(countSql);

		PageImpl<T> page = new PageImpl<T>();
		page.setTotalCount(totalCount);
		page.setList(list);
		return page;
	}

	@Override
	public <T> Page<T> queryForPage(String sql, Class<T> elementType, Object... params) {
		StatementParameter param = toStatementParameter(sql, params);
		List<T> list = this.queryForList(sql, elementType, param);
		CountSqlParser parser = new CountSqlParser(sql, param);

		// System.err.println("countSQL:" + countSqlParser.getCountSql());
		int totalCount = this.queryForInt(parser.getCountSql(), parser.getCountParam());

		PageImpl<T> page = new PageImpl<T>();
		page.setTotalCount(totalCount);
		page.setList(list);

		if (parser.getSize() != null) {
			page.setNextPage(PageImpl.hasNextPage(totalCount, parser.getStart(), parser.getSize()));
			page.setPageSize(parser.getSize());
		}
		return page;
	}

	@Override
	public <T> Page<T> queryForPage(String sql, Class<T> elementType, StatementParameter param) {
		List<T> list = this.queryForList(sql, elementType, param);
		CountSqlParser parser = new GroupCountSqlParser(sql, param);
		String countSql = parser.getCountSql();
		// System.err.println("countSql:" + countSql);
		int totalCount = this.queryForInt(countSql, parser.getCountParam());
		// System.err.println("countSql:" + countSql + " totalCount:" + totalCount);

		PageImpl<T> page = new PageImpl<T>();
		page.setTotalCount(totalCount);
		page.setList(list);
		if (parser.getSize() != null) {
			page.setNextPage(PageImpl.hasNextPage(totalCount, parser.getStart(), parser.getSize()));
			page.setPageSize(parser.getSize());
		}
		return page;
	}

	@Override
	public <T> Page<T> queryForPage(String sql, Class<T> elementType, StatementParameter param, int start, int size) {
		PageableRowMapperResultSetExtractor<T> extractor = new PageableRowMapperResultSetExtractor<T>(new LeopardBeanPropertyRowMapper<T>(elementType), start, size);
		List<T> list = this.getJdbcTemplate().query(sql, param.getArgs(), extractor);
		int totalCount = extractor.getCount();
		PageImpl<T> page = new PageImpl<T>();
		page.setTotalCount(totalCount);
		page.setList(list);

		page.setPageSize(size);
		page.setNextPage(PageImpl.hasNextPage(totalCount, start, size));

		return page;
	}

	@Override
	public void destroy() {
		if (dataSource != null) {
			if (dataSource instanceof JdbcDataSource) {
				((JdbcDataSource) dataSource).destroy();
			}
			// TODO
		}
	}

	@Override
	public <V extends Number> Map<String, V> queryForMap(String sql, Class<V> valueClazz) {
		try {
			return this.getJdbcTemplate().queryForObject(sql, new CountColumnMapRowMapper<>());
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public <V extends Number> Map<String, V> queryForMap(String sql, Class<V> valueClazz, Object... params) {
		try {
			return this.getJdbcTemplate().queryForObject(sql, new CountColumnMapRowMapper<>(), toStatementParameter(sql, params));
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public boolean updateByBean(String tableName, Object bean, String... primaryKeyFieldNames) {
		Class<?> clazz = bean.getClass();
		Field[] fields = clazz.getDeclaredFields();
		if (fields.length == 0) {
			throw new RuntimeException("对象[" + clazz.getName() + "]的属性列表不能为空.");
		}

		Set<String> primaryKeyFieldNameSet = new LinkedHashSet<>();
		if (primaryKeyFieldNames.length == 0) {// 没有设置主键，则获取第一个
			primaryKeyFieldNameSet.add(fields[0].getName());
		}
		else {
			for (String primaryKeyFieldName : primaryKeyFieldNames) {
				primaryKeyFieldNameSet.add(primaryKeyFieldName);
			}
		}

		UpdateBuilder builder = new NullUpdateBuilder(tableName);

		for (Field field : fields) {
			String fieldName = field.getName();

			// TODO 除了主键没有其他字段，要报错
			Class<?> type = field.getType();
			field.setAccessible(true);
			Object obj;
			try {
				obj = field.get(bean);
			}
			catch (IllegalAccessException e) {
				throw new InvalidDataAccessApiUsageException(e.getMessage());
			}
			if (primaryKeyFieldNameSet.contains(fieldName)) {// 设置主键
				this.setFieldValue(builder.where, type, fieldName, obj);
			}
			else {
				this.setFieldValue(builder, type, fieldName, obj);
				continue;
			}
		}
		// builder.where.set
		return this.updateForBoolean(builder);

	}

	protected void setFieldValue(AbstractSqlBuilder builder, Class<?> type, String fieldName, Object obj) {
		if (String.class.equals(type)) {
			builder.setString(fieldName, (String) obj);
		}
		else if (boolean.class.equals(type) || Boolean.class.equals(type)) {
			builder.setBool(fieldName, (Boolean) obj);
		}
		else if (int.class.equals(type) || Integer.class.equals(type)) {
			builder.setInt(fieldName, (Integer) obj);
		}
		else if (long.class.equals(type) || Long.class.equals(type)) {
			builder.setLong(fieldName, (Long) obj);
		}
		else if (float.class.equals(type) || Float.class.equals(type)) {
			builder.setFloat(fieldName, (Float) obj);
		}
		else if (double.class.equals(type) || Double.class.equals(type)) {
			builder.setDouble(fieldName, (Double) obj);
		}
		else if (Month.class.equals(type)) {
			builder.setString(fieldName, ((Month) obj).toString());
		}
		else if (Date.class.equals(type)) {
			builder.setDate(fieldName, (Date) obj);
		}
		else if (List.class.equals(type)) {
			builder.setList(fieldName, (List<?>) obj);
			// builder.setString(fieldName, obj.toString());
		}
		else if (type.isEnum()) {
			if (Snum.class.isAssignableFrom(type)) {
				builder.setSnum(fieldName, (Snum) obj);
			}
			else if (Inum.class.isAssignableFrom(type)) {
				builder.setEnum(fieldName, (Inum) obj);// TODO ?
			}
			else if (Bnum.class.isAssignableFrom(type)) {
				builder.setEnum(fieldName, (Bnum) obj);// TODO ?
			}
			else {
				throw new RuntimeException("未知枚举类型[" + type.getName() + "].");
			}
		}
		else if (DynamicEnum.class.isAssignableFrom(type)) {
			if (Snum.class.isAssignableFrom(type)) {
				builder.setSnum(fieldName, (Snum) obj);
			}
			else if (Inum.class.isAssignableFrom(type)) {
				builder.setEnum(fieldName, (Inum) obj);// TODO ?
			}
			else if (Bnum.class.isAssignableFrom(type)) {
				builder.setEnum(fieldName, (Bnum) obj);// TODO ?
			}
			else {
				throw new RuntimeException("未知动态枚举类型[" + type.getName() + "].");
			}
		}
		else {
			String json = Json.toJson(obj);
			builder.setString(fieldName, json);
			// FIXME 未测试 20200713
			// throw new InvalidDataAccessApiUsageException("未知数据类型[" + type.getName() + "].");
		}
	}

	@Override
	public Date queryForDate(String sql, Object... params) {
		return this.queryForDate(sql, this.toStatementParameter(sql, params));
	}

	@Override
	public <K, V extends Number> Map<K, V> countForMap(String sql, Class<V> keyClazz, Class<V> valueClazz, Object... params) {
		try {
			return this.getJdbcTemplate().query(sql, new CountResultSetExtractor<K, V>(keyClazz, valueClazz));
		}
		catch (EmptyResultDataAccessException e) {
			return new LinkedHashMap<>();
		}
	}
}
