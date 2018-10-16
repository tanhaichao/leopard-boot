package com.company.example.article;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.leopard.json.Json;
import io.leopard.test.IntegrationTests;

public class ArticleServiceImplTest extends IntegrationTests {

	@Autowired
	private ArticleServiceImpl articleService;

	@Test
	public void get() {
		Article article = articleService.get("articleId");
		Json.printFormat(article, "article");
	}

}