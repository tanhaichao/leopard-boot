package io.leopard.boot.freemarker.util;

import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 查询字符串构造器
 * 
 * @author 谭海潮
 *
 */
public class QueryStringBuilder {
	// exist :true value:[] values:[""] queryString:name
	// exist :true value:[] values:[""] queryString:name=
	// exist :true value:[1] values:["1",""] queryString:name=1&name
	// exist :true value:[1] values:["1",""] queryString:name=1&name=

	// exist :false value:[null] values:null queryString:name1=

	/**
	 * URL
	 */
	private String url;

	/**
	 * 参数
	 */
	private Map<String, String[]> parameters = new LinkedHashMap<>();

	public QueryStringBuilder() {

	}

	// public static QueryStringBuilder buildByUri(String uri) {
	// int index = uri.indexOf("?");
	// String queryString;
	// if (index == -1) {
	// queryString = "";
	// }
	// else {
	// queryString = uri.substring(index + 1);
	// }
	// QueryStringBuilder builder = new QueryStringBuilder(queryString);
	// builder.setUrl(uri.substring(0, index));
	// return builder;
	// }

	public static QueryStringBuilder buildByUrl(String url) {
		if (url == null) {
			return new QueryStringBuilder((String) null);
		}
		// URL oUrl = new URL(url);
		int index = url.indexOf("?");
		if (index == -1) {
			return new QueryStringBuilder(url, null);
		}
		else {
			String queryString = url.substring(index + 1);
			return new QueryStringBuilder(url.substring(0, index), queryString);
		}
	}

	public QueryStringBuilder(URL url) {
		this(url.getQuery());
	}

	public QueryStringBuilder(String url, String queryString) {
		this(queryString);
		if (url.indexOf("?") > -1) {
			throw new IllegalArgumentException("url不能含有“?”.");
		}
		this.url = url;
	}

	public QueryStringBuilder(String queryString) {
		if (StringUtils.hasLength(queryString)) {
			String[] blocks = queryString.split("&");
			for (String block : blocks) {
				this.addBlock(block);
			}
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public QueryStringBuilder addBlock(String block) {
		int index = block.indexOf("=");
		if (index == -1) {// 只有名称，没有值的情况
			this.addParameter(block, (String) null);
		}
		else {
			String name = block.substring(0, index);
			String value = block.substring(index + 1);
			this.addParameter(name, value);
		}
		return this;
	}

	public QueryStringBuilder setBlock(String block) {
		int index = block.indexOf("=");
		if (index == -1) {// 只有名称，没有值的情况
			this.setParameter(block, (String) null);
		}
		else {
			String name = block.substring(0, index);
			String value = block.substring(index + 1);
			this.setParameter(name, value);
		}
		return this;
	}

	public QueryStringBuilder(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		for (Entry<String, String[]> entry : parameterMap.entrySet()) {
			String name = entry.getKey();
			String[] values = entry.getValue();
			this.addParameter(name, values);
		}
	}

	/**
	 * 替换参数，不存在则新增(如果已存在多个值，会替换成只有一个值)
	 * 
	 * @param name
	 * @param value
	 */
	public QueryStringBuilder setParameter(String name, String value) {
		setParameter(name, new String[] { value });
		return this;
	}

	public QueryStringBuilder setParameter(String name, String... values) {
		Assert.notNull(name, "参数名称不能为空.");
		this.parameters.put(name, values);
		return this;
	}

	public QueryStringBuilder addParameter(String name, String value) {
		addParameter(name, new String[] { value });
		return this;
	}

	public QueryStringBuilder addParameter(String name, String... values) {
		Assert.notNull(name, "参数名称不能为空.");
		String[] oldValues = this.parameters.get(name);
		if (oldValues != null) {
			String[] newValues = new String[oldValues.length + values.length];
			System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
			System.arraycopy(values, 0, newValues, oldValues.length, values.length);
			this.parameters.put(name, newValues);
		}
		else {
			this.parameters.put(name, values);
		}
		return this;
	}

	public QueryStringBuilder setParameters(Map<String, ?> params) {
		Assert.notNull(params, "参数不能为null");
		for (Entry<String, ?> entry : params.entrySet()) {
			String name = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof String) {
				setParameter(name, (String) value);
			}
			else if (value instanceof String[]) {
				setParameter(name, (String[]) value);
			}
			else {
				throw new IllegalArgumentException("参数[" + name + "]不是字符串或字符串数组[" + value.getClass().getName() + "].");
			}
		}
		return this;
	}

	public QueryStringBuilder addParameters(Map<String, ?> params) {
		Assert.notNull(params, "参数不能为null");
		for (Entry<String, ?> entry : params.entrySet()) {
			String name = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof String) {
				addParameter(name, (String) value);
			}
			else if (value instanceof String[]) {
				addParameter(name, (String[]) value);
			}
			else {
				throw new IllegalArgumentException("参数[" + name + "]不是字符串或字符串数组[" + value.getClass().getName() + "].");
			}
		}
		return this;
	}

	public String toUrl() {
		StringBuilder sb = new StringBuilder();
		if (this.url != null) {
			sb.append(url);
		}
		String queryString = this.toQueryString();
		if (queryString.length() > 0) {
			sb.append("?");
			sb.append(queryString);
		}
		return sb.toString();
	}

	/**
	 * 返回新的QueryString.
	 * 
	 * @return
	 */
	public String toQueryString() {

		// if (parameters.isEmpty()) {
		// return null;
		// }
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String[]> parameter : parameters.entrySet()) {
			String name = parameter.getKey();
			String[] values = parameter.getValue();
			// System.err.println("name:" + name + " valueList:" + valueList);
			if (values != null) {
				for (String value : values) {
					if (sb.length() > 0) {
						sb.append("&");
					}
					// FIXME 参数名称和参数值合法性判断
					sb.append(name);
					if (value != null) {
						sb.append("=");
						sb.append(value);
					}
				}
			}
		}
		return sb.toString();
	}

	protected static String getQueryString(HttpServletRequest request, String key, String value) {
		// url = url.substring(0, url.indexOf("?") == -1 ? url.length() : url.indexOf("?"));
		StringBuilder sb = new StringBuilder();
		Enumeration<String> e = request.getParameterNames();

		boolean hasReplace = false;
		while (e.hasMoreElements()) {
			String name = e.nextElement();
			String value2;
			if (name.equals(key)) {
				value2 = value;
				hasReplace = true;
			}
			else {
				value2 = request.getParameter(name);
			}
			if (sb.length() > 0) {
				sb.append("&");
			}
			// FIXME 参数名称和参数值合法性判断
			sb.append(name).append("=").append(value2);
		}
		if (!hasReplace) {
			if (sb.length() > 0) {
				sb.append("&");
			}
			sb.append(key).append("=").append(value);
		}
		return "?" + sb.toString();
	}
}
