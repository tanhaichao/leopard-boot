package io.leopard.archetype.example.article;

/**
 * 文章
 * 
 * @author 谭海潮
 *
 */
public interface ArticleService {
	/**
	 * 添加文章
	 */

	boolean add(Article article);

	/**
	 * 根据主键查询文章
	 */

	Article get(String articleId);

	/**
	 * 根据主键删除文章
	 */
	boolean delete(String articleId, long opuid);

	/**
	 * 下一条文章
	 * 
	 * @param articleId 文章ID
	 * @return
	 */
	Article next(String articleId);

	/**
	 * 上一条文章
	 * 
	 * @param articleId 文章ID
	 * @return
	 */
	Article previous(String articleId);

	/**
	 * 更新文章
	 * 
	 * @param article
	 * @return
	 */
	boolean update(Article article);
}
