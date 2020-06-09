package io.leopard.archetype.example.article;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.leopard.archetype.example.util.CheckUtil;
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

	@Override
	public boolean update(Article article) {
		return articleDao.update(article);
	}

	@Override
	public Article next(String articleId) {
		return articleDao.next(articleId);
	}

	@Override
	public Article previous(String articleId) {
		return articleDao.previous(articleId);
	}
}
