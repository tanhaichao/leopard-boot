package io.leopard.boot.weixin.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlUtils {

	// private static final Log logger = LogFactory.getLog(XmlUtils.class);

	public static String cdata(String content) {
		return "<![CDATA[" + content + "]]>";
	}

	public static Element toRootElement(String content) throws IOException {
		InputStream input = new ByteArrayInputStream(content.getBytes());
		Element root = read(input).getDocumentElement();
		input.close();
		return root;
	}

	public static Document read(InputStream input) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			dbf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
			dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
			dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
			dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			dbf.setXIncludeAware(false);
			dbf.setExpandEntityReferences(false);
			DocumentBuilder db = dbf.newDocumentBuilder();
			return db.parse(input);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static String escape(String input) {
		if (input == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		StringCharacterIterator iterator = new StringCharacterIterator(input);
		char character = iterator.current();
		while (character != CharacterIterator.DONE) {
			if (character == '<') {
				result.append("&lt;");
			}
			else if (character == '>') {
				result.append("&gt;");
			}
			else if (character == '\"') {
				result.append("&quot;");
			}
			else if (character == '\'') {
				result.append("&#039;");
			}
			else if (character == '&') {
				result.append("&amp;");
			}
			else {
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}

	// public static Element getRootElement(String url) {
	// return getRootElement(url, -1);
	// }

	// public static Element getRootElement(String url, long timeout) {
	// String content = Httpnb.doGet(url, timeout).trim();
	// // System.out.println("content:" + content);
	// if (content == null || content.length() == 0) {
	// logger.warn("result is empty, url:" + url);
	// return null;
	// }
	// return toRootElement(content);
	// }

}
