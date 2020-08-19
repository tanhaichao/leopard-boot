package io.leopard.httpnb;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;

public interface HttpHeader {

	void setTimeout(long timeout);

	long getTimeout();

	void setCookie(String cookie);

	void setMethod(String method);

	HttpURLConnection openConnection(String url) throws IOException;

	void addParam(Param param);

	void setUserAgent(String userAgent);

	Proxy getProxy();

	void setProxy(Proxy proxy);

	String getContentType();

	void setContentType(String contentType);
}
