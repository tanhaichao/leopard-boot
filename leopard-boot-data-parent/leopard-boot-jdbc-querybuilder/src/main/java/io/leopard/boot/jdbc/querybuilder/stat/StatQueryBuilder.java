package io.leopard.boot.jdbc.querybuilder.stat;

import java.util.List;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.StatementParameter;
import io.leopard.lang.Page;
import io.leopard.lang.PageImpl;

/**
 * 统计查询
 * 
 * @author 谭海潮
 *
 */
public class StatQueryBuilder {

	/**
	 * SELECT语句
	 */
	private StringBuilder select = new StringBuilder();

	/**
	 * 记录条数统计
	 */
	CounterBuilder counter;

	/**
	 * 搜索条件
	 */
	private StatSearchBuilder search;

	private String groupby;

	public StatQueryBuilder(StatSearchBuilder search, CounterBuilder counter) {
		this.search = search;
		this.counter = counter;
	}

	public StringBuilder append(String str) {
		return select.append(str);
	}

	public void groupby(String groupby) {
		this.groupby = groupby;
	}

	public <T> Page<T> queryForPage(Jdbc jdbc, Class<T> elementType, int start, int size) {
		StatementParameter param = new StatementParameter();
		String sql = select.toString() + search.generateWhereSQL(param) + " limit ?,?";
		if (groupby != null) {
			sql += "group by " + groupby;
		}
		System.err.println("select:" + sql);
		param.setInt(start);
		param.setInt(size);
		List<T> list = jdbc.queryForList(sql, elementType, param);

		int totalCount = counter.count(jdbc);

		PageImpl<T> page = new PageImpl<T>();
		page.setTotalCount(totalCount);
		page.setList(list);
		page.setNextPage(PageImpl.hasNextPage(totalCount, start, size));
		page.setPageSize(size);

		return page;
	}
}
