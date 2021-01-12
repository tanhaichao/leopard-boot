package io.leopard.boot.elasticsearch;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;

public interface ESClient {

	boolean createIndex(String indexName) throws IOException;

	/**
	 * 添加记录
	 * 
	 * @param index 索引名称
	 * @param type 类型名称
	 * @param id ID
	 * @param json 数据
	 * @return
	 * @throws IOException
	 */
	String add(String indexName, String type, String id, String json) throws IOException;

	IndexResponse index(String indexName, String type, String id, String json) throws IOException;

	/**
	 * 根据主键查询
	 * 
	 * @param index 索引名称
	 * @param type 类型名称
	 * @param id ID
	 * @return
	 * @throws IOException
	 */
	GetResponse get(String indexName, String type, String id) throws IOException;

	SearchHits search(String indexName, QueryBuilder query, int start, int size) throws IOException;

	boolean clean(String indexName) throws IOException;

	/**
	 * 删除
	 * 
	 * @param indexName 索引名称
	 * @param type 类型名称
	 * @param id ID
	 * @return
	 * @throws IOException
	 */
	String delete(String indexName, String type, String id) throws IOException;

	String delete(String indexName, String type, long id) throws IOException;

	SearchHits search(String indexName, QueryBuilder query, String orderField, int start, int size) throws IOException;

	SearchHits search(String indexName, QueryBuilder query, String orderField, SortOrder order, int start, int size) throws IOException;

	String add(String indexName, String type, long id, String json) throws IOException;

	GetResponse get(String indexName, String type, long id) throws IOException;

	/**
	 * 查询索引是否存在
	 * 
	 * @param indexName
	 * @return
	 * @throws IOException
	 */
	boolean exists(String indexName) throws IOException;

	boolean deleteIndex(String indexName) throws IOException;

	boolean createIndex(String indexName, Map<String, Object> mapping) throws IOException;

}
