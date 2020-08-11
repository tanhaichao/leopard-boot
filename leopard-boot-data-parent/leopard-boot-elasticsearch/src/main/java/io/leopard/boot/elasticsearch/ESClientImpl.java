package io.leopard.boot.elasticsearch;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

@Component
public class ESClientImpl extends AbstractESClient {

	@Override
	public boolean createIndex(String indexName) throws IOException {
		CreateIndexRequest request = new CreateIndexRequest(indexName);
		request.settings(Settings.builder().put("index.number_of_shards", 3).put("index.number_of_replicas", 2));

		// CreateIndexResponse response = ;
		this.restClient.indices().create(request, RequestOptions.DEFAULT);
		return true;
	}

	@Override

	public String add(String indexName, String type, long id, String json) throws IOException {
		return this.add(indexName, type, Long.toString(id), json);
	}

	@Override
	public String add(String indexName, String type, String id, String json) throws IOException {
		IndexResponse response = this.index(indexName, type, id, json);
		return response.getId();
	}

	@Override
	public IndexResponse index(String indexName, String type, String id, String json) throws IOException {
		IndexRequest request = new IndexRequest(indexName, type, id);
		request.source(json, XContentType.JSON);
		return this.restClient.index(request, RequestOptions.DEFAULT);
	}

	@Override
	public GetResponse get(String indexName, String type, long id) throws IOException {
		return this.get(indexName, type, Long.toString(id));
	}

	@Override
	public GetResponse get(String indexName, String type, String id) throws IOException {
		GetRequest request = new GetRequest(indexName, type, id);
		return restClient.get(request, RequestOptions.DEFAULT);
	}

	@Override
	public SearchHits search(String indexName, QueryBuilder query, int start, int size) throws IOException {
		return this.search(indexName, query, null, start, size);
	}

	@Override
	public SearchHits search(String indexName, QueryBuilder query, String orderField, int start, int size) throws IOException {
		// SearchResponse response = client.prepareSearch().setQuery(query).addSort(orderField, SortOrder.DESC).setFrom(start).setSize(size).execute().actionGet();
		// return response.getHits();
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(query);
		if (orderField != null) {
			sourceBuilder.sort(orderField, SortOrder.DESC);
		}
		sourceBuilder.from(start);
		sourceBuilder.size(size);
		SearchRequest request = new SearchRequest(indexName);
		request.source(sourceBuilder);
		SearchResponse response = this.restClient.search(request, RequestOptions.DEFAULT);

		return response.getHits();
	}

	@Override
	public boolean exists(String indexName) throws IOException {
		GetIndexRequest request = new GetIndexRequest(indexName);
		return restClient.indices().exists(request, RequestOptions.DEFAULT);
	}

	@Override
	public boolean deleteIndex(String indexName) throws IOException {
		boolean exists = this.exists(indexName);
		if (!exists) {
			return false;
		}
		DeleteIndexRequest request = new DeleteIndexRequest(indexName);
		// request.getShouldStoreResult();
		this.restClient.indices().delete(request, RequestOptions.DEFAULT);

		return true;
	}

	@Override
	public boolean clean(String indexName) throws IOException {
		QueryBuilder query = QueryBuilders.matchAllQuery();
		DeleteByQueryRequest request = new DeleteByQueryRequest(indexName);
		request.setQuery(query);
		restClient.deleteByQuery(request, RequestOptions.DEFAULT);
		return true;
	}

	@Override
	public String delete(String indexName, String type, String id) throws IOException {
		DeleteRequest request = new DeleteRequest(indexName, type, id);
		DeleteResponse response = this.restClient.delete(request, RequestOptions.DEFAULT);
		return response.getId();
	}

}
