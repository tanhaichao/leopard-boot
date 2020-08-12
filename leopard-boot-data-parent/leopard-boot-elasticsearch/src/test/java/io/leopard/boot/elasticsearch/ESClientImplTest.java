package io.leopard.boot.elasticsearch;

import java.io.IOException;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.leopard.json.Json;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ESAutoConfiguration.class)
public class ESClientImplTest {

	@Autowired
	private ESClientImpl esClient;

	@Test
	public void index() throws IOException {
		long newsId = 7;
		News news = new News();
		news.setNewsId(newsId);
		news.setTitle("中国产小型无人机的“对手”来了，俄微型拦截导弹便宜量又多" + newsId);
		news.setTag("军事");
		news.setPublishTime("2018-01-24T23:59:30Z");

		String json = Json.toFormatJson(news);

		esClient.clean("news");
		String id = this.esClient.add("news", "type", newsId, json);

		System.out.println("response :" + id);

		GetResponse response = esClient.get("news", "type", newsId);
		System.out.println(response.getSourceAsString());

		System.out.println("搜索=======");
		QueryBuilder query = QueryBuilders.matchQuery("title", "产小");

		// QueryBuilder query = QueryBuilders.boolQuery()//
		// // .must(QueryBuilders.wildcardQuery("title", "来了"))//
		// // .mustNot(QueryBuilders.termQuery("message", "nihao"))//
		// // .should(QueryBuilders.termQuery("gender", "male"))//
		// ;
		SearchHits hits = esClient.search("news", query, 0, 10);
		System.out.println("hits:" + hits.totalHits);
		for (SearchHit hit : hits.getHits()) {
			// String title = hit.getField("title").getValue();
			System.out.println("hit string:" + hit.getSourceAsString());
		}
	}

}