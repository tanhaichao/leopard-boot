package com.company.example.article;

import java.util.Date;

import io.leopard.data4j.cache.api.uid.IDelete;

/**
 * 文章
 * 
 * @author 谭海潮
 *
 */
public interface ArticleDao extends IDelete<Article, String> {
	/**
	 * 添加文章
	 */
	@Override
	boolean add(Article article);

	/**
	 * 根据主键查询文章
	 */
	@Override
	Article get(String articleId);

	/**
	 * 根据主键删除文章
	 */
	@Override
	boolean delete(String articleId, long opuid, Date lmodify);
}
