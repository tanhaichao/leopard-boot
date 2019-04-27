package io.leopard.boot.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JavaScript视图
 * 
 * @author 阿海
 * 
 */
public class JavaScriptView extends AbstractView {

	private String script;

	public JavaScriptView(String script) {
		this.script = script;
	}

	public String getScript() {
		return this.script;
	}

	@Override
	public String getContentType() {
		return "application/javascript; charset=UTF-8";
	}

	@Override
	public String getBody(HttpServletRequest request, HttpServletResponse response) {
		return script;
	}
}