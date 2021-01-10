package io.leopard.boot.elasticsearch;

import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;

public class QueryBuilderTest {

	@Test
	public void test() {
		PrefixQueryBuilder q = QueryBuilders.prefixQuery("name", "abc");
		System.out.println(q);
	}
}
