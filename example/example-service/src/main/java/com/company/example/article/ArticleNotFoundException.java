package com.company.example.article;

import io.leopard.core.exception.NotFoundException;

/**
 * 文章不存在
 * 
 * @author 谭海潮
 *
 */
public class ArticleNotFoundException extends NotFoundException {

	private static final long serialVersionUID = 1L;

	public ArticleNotFoundException(String articleId) {
		super("文章[" + articleId + "]不存在.");
	}

}
