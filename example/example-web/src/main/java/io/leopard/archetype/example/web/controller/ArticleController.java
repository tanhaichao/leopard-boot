package io.leopard.archetype.example.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.archetype.example.article.Article;
import io.leopard.archetype.example.article.ArticleNotFoundException;
import io.leopard.archetype.example.article.ArticleService;
import io.leopard.archetype.example.web.form.ArticleForm;
import io.leopard.archetype.example.web.vo.ArticleListVO;
import io.leopard.archetype.example.web.vo.ArticleVO;
import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.builder.QueryBuilder;
import io.leopard.lang.Page;
import io.leopard.lang.datatype.TimeRange;
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

	@Autowired
	private Jdbc jdbc;

	/**
	 * 添加
	 * 
	 * @param sessUid 当前登录用户ID
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
	 * 列表
	 * 
	 * @param uid 用户ID
	 * @param start 分页其实位置
	 * @param size 每页记录条数
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public Page<ArticleListVO> list(long uid, TimeRange range, int start, int size) {
		QueryBuilder builder = new QueryBuilder("article");
		builder.addLong("uid", uid);
		builder.range("posttime", range);
		builder.order("posttime");
		return builder.queryForPage(jdbc, ArticleListVO.class, start, size);
	}

	/**
	 * 详情
	 * 
	 * @param articleId 文章ID
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
	 * 上一条文章
	 * 
	 * @param articleId 文章ID
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public ArticleVO previous(String articleId) {
		Article article = articleSerivce.previous(articleId);
		ArticleVO articleVO = BeanUtil.convert(article, ArticleVO.class);
		return articleVO;
	}

	/**
	 * 下一条文章
	 * 
	 * @param articleId 文章ID
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public ArticleVO next(String articleId) {
		Article article = articleSerivce.next(articleId);
		ArticleVO articleVO = BeanUtil.convert(article, ArticleVO.class);
		return articleVO;
	}

	/**
	 * 删除
	 * 
	 * @param sessUid 当前登录用户ID
	 * @param articleId 文章ID
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public boolean delete(long sessUid, String articleId) {
		return articleSerivce.delete(articleId, 0);
	}

	/**
	 * 更新文章
	 * 
	 * @param articleId 文章ID
	 * @param form 表单信息
	 * @return
	 * @throws ArticleNotFoundException
	 */
	@RequestMapping
	@ResponseBody
	public boolean update(long sessUid, String articleId, ArticleForm form) throws ArticleNotFoundException {
		Article article = articleSerivce.get(articleId);
		if (article == null) {
			throw new ArticleNotFoundException(articleId);
		}
		BeanUtil.copyProperties(form, article);
		return articleSerivce.update(article);
	}

}
