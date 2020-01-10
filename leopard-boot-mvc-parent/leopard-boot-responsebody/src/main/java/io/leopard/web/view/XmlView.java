package io.leopard.web.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * XML.
 * 
 * @author 阿海
 * 
 */
public class XmlView extends AbstractView {

	private String message;

	public XmlView(final String message) {
		// this.addObject("message", data);
		this.message = message;
	}

	public String getMessage() {
		// return (String) this.getModel().get("message");
		return this.message;
	}

	@Override
	public String getContentType() {
		return "application/xml; charset=UTF-8";
	}

	@Override
	public String getBody(HttpServletRequest request, HttpServletResponse response) {
		String message = this.getMessage();
		return message;
	}
}
