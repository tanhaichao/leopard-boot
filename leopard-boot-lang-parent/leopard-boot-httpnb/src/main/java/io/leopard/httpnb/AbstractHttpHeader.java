package io.leopard.httpnb;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class AbstractHttpHeader implements HttpHeader {

	private long timeout = -1;

	private String cookie;

	protected String method = "GET";

	private String userAgent = null;

	private String contentType = null;

	protected String authorization;

	protected List<Param> paramList = new ArrayList<Param>();

	// public AbstractHttpHeader(String method, long timeout) {
	// this.setMethod(method);
	// this.setTimeout(timeout);
	// }

	@Override
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	@Override
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	@Override
	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public HttpURLConnection openConnection(String url) throws IOException {
		boolean isHttps = url.startsWith("https");
		if (isHttps) {
			Https.trustAllHosts();
		}
		URL oUrl = new URL(url);
		HttpURLConnection conn;
		if (this.proxy == null) {
			conn = (HttpURLConnection) oUrl.openConnection();
		}
		else {
			conn = (HttpURLConnection) oUrl.openConnection(proxy);
		}
		if (isHttps) {
			((HttpsURLConnection) conn).setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
		}

		if (timeout > 0) {
			conn.setConnectTimeout((int) timeout);
			conn.setReadTimeout((int) timeout);
		}
		if (cookie != null && cookie.length() > 0) {
			conn.setRequestProperty("Cookie", cookie);
		}
		if (userAgent != null && userAgent.length() > 0) {
			conn.setRequestProperty("user-agent", userAgent);
		}
		if (contentType != null && contentType.length() > 0) {
			conn.setRequestProperty("Content-Type", this.contentType);
		}
		conn.setRequestProperty("X-Real-IP", "127.0.0.1");// FIXME ahai 测试代码

		return conn;
	}

	@Override
	public long getTimeout() {
		return timeout;
	}

	@Override
	public void addParam(Param param) {
		this.paramList.add(param);
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	@Override
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	protected Proxy proxy;

	@Override
	public Proxy getProxy() {
		return proxy;
	}

	@Override
	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
