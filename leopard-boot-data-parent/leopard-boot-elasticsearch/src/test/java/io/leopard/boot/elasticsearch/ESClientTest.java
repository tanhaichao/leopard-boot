package io.leopard.boot.elasticsearch;

import java.io.IOException;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;

import io.leopard.json.Json;

public class ESClientTest {

	@Test
	public void getRestClient() throws IOException {
		ESClientImpl client = new ESClientImpl();
		client.init();
		GetRequest getRequest = new GetRequest("customer", "type", "1");
		GetResponse response = client.getRestClient().get(getRequest);
		String json = response.getSourceAsString();
		System.out.println(json);
		client.close();
	}

	@Test
	public void index() {

		News news = new News();
		news.setTitle("中国产小型无人机的“对手”来了，俄微型拦截导弹便宜量又多");
		news.setTag("军事");
		news.setPublishTime("2018-01-24T23:59:30Z");
		String json = Json.toFormatJson(news);
		System.out.println("json:" + json);
		IndexRequest indexRequest = new IndexRequest("news", "type");
		indexRequest.source(json, XContentType.JSON);

	}

}