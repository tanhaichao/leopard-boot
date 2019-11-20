package io.leopard.boot.freemarker.util;

import java.net.MalformedURLException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public class QueryStringBuilderTest {

	@Test
	public void toQueryString() {
		Assert.assertEquals("", new QueryStringBuilder().toQueryString());
		Assert.assertEquals("name1=value1", new QueryStringBuilder().setParameter("name1", "value1").toQueryString());
		Assert.assertEquals("name1=value0&name1=value1", new QueryStringBuilder("name1=value0").addParameter("name1", "value1").toQueryString());
		Assert.assertEquals("name1=&name1=value1", new QueryStringBuilder("name1=").addParameter("name1", "value1").toQueryString());
		Assert.assertEquals("name1&name1=value1", new QueryStringBuilder("name1").addParameter("name1", "value1").toQueryString());
		Assert.assertEquals("name1&name1=value1&name2", new QueryStringBuilder("name1").addParameter("name1", "value1").addParameter("name2", (String) null).toQueryString());
	}

	@Test
	public void request() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("name1", "value1");
		String queryString = new QueryStringBuilder().setParameter("name1", "value1").toQueryString();
		Assert.assertEquals("name1=value1", queryString);

	}

	@Test
	public void buildByUrl() throws MalformedURLException {
		Assert.assertEquals("name1=value1", QueryStringBuilder.buildByUrl("http://localhost/searcho.do?name1=value1").toQueryString());
		Assert.assertEquals("keyword=", QueryStringBuilder.buildByUrl("/search.do?keyword=").toQueryString());
	}

	@Test
	public void toUrl() throws MalformedURLException {
		Assert.assertEquals("/search.do?keyword=", QueryStringBuilder.buildByUrl("/search.do?keyword=").toUrl());
	}
	// @Test
	// public void buildByUri() {
	// Assert.assertEquals("name1=value1", QueryStringBuilder.buildByUri("searcho.do?name1=value1").toQueryString());
	// }
}