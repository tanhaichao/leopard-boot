package com.company.example.article;

import io.leopard.core.exception.InvalidException;

/**
 * 非法文章ID
 * 
 * @author 谭海潮
 *
 */
public class ArticleIdInvalidException extends InvalidException {


	private static final long serialVersionUID = 1L;

	public ArticleIdInvalidException(String articleId) {
		super("非法文章[" + articleId + "]ID.");
	}

}
