package io.leopard.boot.freemarker.method;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import freemarker.template.TemplateModelException;

/**
 * URL编码
 * 
 * @author 阿海
 *
 */
@Component
public class UrlEncodeTemplateMethod extends AbstractTemplateMethod {

	@Override
	public Object execute(HttpServletRequest request, Object... args) throws TemplateModelException {
		String str = (String) args[0];
		if (str == null) {
			return null;
		}
		try {
			return URLEncoder.encode(str, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public String getKey() {
		return "urlEncode";
	}

}
