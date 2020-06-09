package io.leopard.archetype.example.util;

import io.leopard.boot.util.LeopardValidUtil;
import io.leopard.boot.util.UuidUtil;

/**
 * 参数合法性验证
 * 
 * @author 谭海潮
 */
public class ValidUtil extends LeopardValidUtil {

	/**
	 * 判断文章ID是否合法
	 * 
	 * @param articleId 文章ID
	 * @return
	 */
	public static boolean isArticleId(String articleId) {
		return UuidUtil.isUuid(articleId);
	}
}
