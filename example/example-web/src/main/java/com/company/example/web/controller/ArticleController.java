package com.company.example.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.example.article.Article;
import com.company.example.article.ArticleService;
import com.company.example.web.form.ArticleForm;
import com.company.example.web.vo.ArticleVO;

import io.leopard.lang.util.BeanUtil;

/**
 * 文章
 * 
 * @author 谭海潮
 *
 */
@Controller
@RequestMapping("/article/")
public class ArticleController {

	@Autowired
	private ArticleService articleSerivce;

	/**
	 * 添加
	 * 
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public boolean add(long sessUid, ArticleForm form) {
		Article article = BeanUtil.convert(form, Article.class);
		article.setUid(sessUid);
		articleSerivce.add(article);
		return true;
	}

	/**
	 * 详情
	 * 
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public ArticleVO get(String articleId) {
		Article article = articleSerivce.get(articleId);
		ArticleVO articleVO = BeanUtil.convert(article, ArticleVO.class);
		return articleVO;
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public boolean delete(long sessUid, String articleId) {
		return articleSerivce.delete(articleId, 0);
	}
}
