package io.leopard.boot.elasticsearch;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;

public class KeywordQueryBuilder extends BoolQueryBuilder {

	public MatchQueryBuilder matchQuery(String name, String keyword) {
		MatchQueryBuilder q = QueryBuilders.matchQuery(name, keyword);
		q.boost(1.1f);
		this.should(q);
		return q;
	}

	public WildcardQueryBuilder likeQuery(String name, String keyword) {
		WildcardQueryBuilder q = QueryBuilders.wildcardQuery(name, SearchUtil.getLikeKeyword(keyword));
		this.should(q);
		return q;
	}

}
