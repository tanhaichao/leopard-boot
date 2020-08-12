package io.leopard.boot.elasticsearch;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestClientBuilder.RequestConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractESClient implements ESClient {

	@Value("elasticsearch.host")
	private String host;

	@Value("elasticsearch.port")
	private int port = 9200;

	protected String clusterName;

	////
	public int connectTimeoutMillis = 1000;

	public int socketTimeoutMillis = 30000;

	public int CONNECTION_REQUEST_TIMEOUT_MILLIS = 500;

	public int maxConnectionPerRoute = 10;

	public int maxConnection = 30;

	private RestClientBuilder builder;

	protected RestHighLevelClient restClient;

	@PostConstruct
	public void init() {
		HttpHost httpHost = new HttpHost(host, port);
		builder = RestClient.builder(httpHost);
		setConnectTimeOutConfig();
		setMutiConnectConfig();
		restClient = new RestHighLevelClient(builder);
		System.out.println("init factory");
	}

	protected void setConnectTimeOutConfig() { // 配置连接时间延时
		builder.setRequestConfigCallback(new RequestConfigCallback() {

			@Override
			public Builder customizeRequestConfig(Builder builder) {
				builder.setConnectTimeout(connectTimeoutMillis);
				builder.setSocketTimeout(socketTimeoutMillis);
				builder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT_MILLIS);
				return builder;
			}
		});
	}

	protected void setMutiConnectConfig() { // 使用异步httpclient时设置并发连接数
		builder.setHttpClientConfigCallback(new HttpClientConfigCallback() {

			@Override
			public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
				httpClientBuilder.setMaxConnTotal(maxConnection);
				httpClientBuilder.setMaxConnPerRoute(maxConnectionPerRoute);
				return httpClientBuilder;
			}
		});
	}

	protected Settings.Builder getSettingsBuilder() {
		if (clusterName == null || clusterName.length() == 0) {
			return Settings.builder();
		}
		else {
			return Settings.builder().put("cluster.name", this.clusterName);
		}
	}

	public RestHighLevelClient getRestClient() {
		return restClient;
	}

	@PreDestroy
	public void close() {
		if (restClient != null) {
			try {
				restClient.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("close client");
	}
}
