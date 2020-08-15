package io.leopard.email;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class MailTemplateUtil {

	public static String getTemplate(String templatePath) throws IOException {
		ClassPathResource resource = new ClassPathResource(templatePath);
		InputStream input = resource.getInputStream();
		String content = IOUtils.toString(input, "UTF-8");
		input.close();
		return content;
	}

	public static String render(String templatePath, Map<String, Object> model) throws IOException, TemplateException {
		String template = getTemplate(templatePath);// TODO 可以考虑加上缓存
		Template t = new Template("", new StringReader(template), null);
		StringWriter out = new StringWriter();
		t.process(model, out);
		String content = out.toString();
		return content;
	}
}
