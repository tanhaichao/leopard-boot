package io.leopard.jdbc;

import java.util.Date;

import io.leopard.jdbc.exception.RecordNotFoundException;

public class JdbcQuerier {
	/**
	 * 下一条记录
	 * 
	 * @param jdbc
	 * @param tableName 表名
	 * @param idFieldName ID属性名称
	 * @param id ID值
	 * @param elementType
	 * @return
	 */
	public static <T> T next(Jdbc jdbc, String tableName, String idFieldName, String id, Class<T> elementType) {
		return next(jdbc, tableName, idFieldName, id, elementType, "posttime");
	}

	/**
	 * 下一条记录
	 * 
	 * @param jdbc
	 * @param idFieldName ID属性名称
	 * @param id ID值
	 * @param posttimeFieldName 发表时间属性名称
	 * @return
	 */
	public static <T> T next(Jdbc jdbc, String tableName, String idFieldName, String id, Class<T> elementType, String posttimeFieldName) {
		String sql = "select `" + posttimeFieldName + "` from `" + tableName + "` where `" + idFieldName + "`=?";
		Date posttime = jdbc.queryForDate(sql, id);
		if (posttime == null) {
			throw new RecordNotFoundException("记录[tableName:" + tableName + " id:" + id + "]不存在.");
		}
		String nextSql = "select * from `` where `" + posttimeFieldName + "`>=? order by `" + posttimeFieldName + " asc, `" + idFieldName + "` asc`' limit 1";
		return jdbc.query(nextSql, elementType, posttime);
	}

	public static <T> T previous(Jdbc jdbc, String tableName, String idFieldName, String id, Class<T> elementType) {
		return previous(jdbc, tableName, idFieldName, id, elementType, "posttime");
	}

	/**
	 * 上一条
	 * 
	 * @param jdbc
	 * @param tableName
	 * @param idFieldName
	 * @param id
	 * @param elementType
	 * @param posttimeFieldName
	 * @return
	 */
	public static <T> T previous(Jdbc jdbc, String tableName, String idFieldName, String id, Class<T> elementType, String posttimeFieldName) {
		String sql = "select `" + posttimeFieldName + "` from `" + tableName + "` where `" + idFieldName + "`=?";
		Date posttime = jdbc.queryForDate(sql, id);
		if (posttime == null) {
			throw new RecordNotFoundException("记录[tableName:" + tableName + " id:" + id + "]不存在.");
		}
		String nextSql = "select * from `` where `" + posttimeFieldName + "`<=? order by `" + posttimeFieldName + " desc, `" + idFieldName + "` desc`' limit 1";
		return jdbc.query(nextSql, elementType, posttime);
	}
}
