package com.company.example.article;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.company.example.util.CheckUtil;

import io.leopard.boot.util.StringUtil;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleDao articleDao;

	@Override
	public boolean add(Article article) {
		if (StringUtils.isEmpty(article)) {
			article.setArticleId(StringUtil.uuid());
		}
		return articleDao.add(article);
	}

	@Override
	public Article get(String articleId) {
		CheckUtil.isArticleId(articleId);
		return articleDao.get(articleId);
	}

	@Override
	public boolean delete(String articleId, long opuid) {
		CheckUtil.isArticleId(articleId);
		return articleDao.delete(articleId, opuid, new Date());
	}
}
