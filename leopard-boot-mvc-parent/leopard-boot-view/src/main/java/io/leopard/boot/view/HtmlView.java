package io.leopard.boot.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HTML视图
 * 
 * @author 阿海
 * 
 */
public class HtmlView extends AbstractView {

	private String html;

	public HtmlView(String html) {
		this.html = html;
	}

	public String getHtml() {
		return this.html;
	}

	@Override
	public String getContentType() {
		return "text/html; charset=UTF-8";
	}

	@Override
	public String getBody(HttpServletRequest request, HttpServletResponse response) {
		return html;
	}
}