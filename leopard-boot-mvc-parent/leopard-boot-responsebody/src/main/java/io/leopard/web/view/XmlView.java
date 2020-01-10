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

	private String xml;

	public XmlView(final String xml) {
		// this.addObject("message", data);
		this.xml = xml;
	}

	public String getXml() {
		// return (String) this.getModel().get("message");
		return this.xml;
	}

	@Override
	public String getContentType() {
		return "application/xml; charset=UTF-8";
	}

	@Override
	public String getBody(HttpServletRequest request, HttpServletResponse response) {
		String xml = this.getXml();
		return xml;
	}
}
