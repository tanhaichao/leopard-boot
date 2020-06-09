package io.leopard.archetype.example.util;

import io.leopard.archetype.example.article.ArticleIdInvalidException;

/**
 * 参数合法性检查
 * 
 * @author 谭海潮
 */
public class CheckUtil {

	/**
	 * 判断文章ID是否合法
	 * 
	 * @param articleId 文章ID
	 */
	public static void isArticleId(String articleId) {
		if (ValidUtil.isArticleId(articleId)) {
			throw new ArticleIdInvalidException(articleId);
		}
	}
}
