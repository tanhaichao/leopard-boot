package io.leopard.boot.jdbc.querybuilder.stat;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.leopard.boot.util.StringUtil;
import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.LeopardBeanPropertyRowMapper;
import io.leopard.jdbc.PageableRowMapperResultSetExtractor;
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
	 * 搜索条件
	 */
	private StatSearchBuilder search;

	public StatQueryBuilder(StatSearchBuilder search) {
		this.search = search;
	}

	public StringBuilder append(String str) {
		return select.append(str);
	}

	protected String replaceWhereExpression(String sql, StatementParameter param) {
		String regex = "\\{where\\}";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String where = search.generateWhereSQL(param);
			m.appendReplacement(sb, StringUtil.escapePattern(where));
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public <T> Page<T> queryForPage(Jdbc jdbc, Class<T> elementType, int start, int size) {
		StatementParameter param = new StatementParameter();
		String sql = select.toString();
		sql = replaceWhereExpression(sql, param);
		System.err.println("select:" + sql);

		PageableRowMapperResultSetExtractor<T> extractor = new PageableRowMapperResultSetExtractor<T>(new LeopardBeanPropertyRowMapper<T>(elementType), start, size);
		List<T> list = jdbc.getJdbcTemplate().query(sql, param.getArgs(), extractor);
		int totalCount = extractor.getCount();
		PageImpl<T> page = new PageImpl<T>();
		page.setTotalCount(totalCount);
		page.setList(list);

		page.setPageSize(size);
		page.setNextPage(PageImpl.hasNextPage(totalCount, start, size));

		return page;

	}
}
