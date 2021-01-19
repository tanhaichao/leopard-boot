package io.leopard.email;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import freemarker.template.TemplateException;

@Component
@ConditionalOnProperty(prefix = "mail", name = "host")
public class MailClientImpl implements MailClient {

	@Value("${mail.host}")
	private String host;

	@Value("${mail.user}")
	private String user;

	@Value("${mail.from}")
	private String from;

	@Value("${mail.port}")
	private int port;

	/**
	 * 发件人名称
	 */
	@Value("${mail.name:}")
	private String name;

	@Value("${mail.password}")
	private String password;

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean sendText(String to, String subject, String content) throws EmailException {
		SimpleEmail email = new SimpleEmail();
		email.setCharset("UTF-8");
		email.setHostName(host);// 设置使用发电子邮件的邮件服务器
		email.addTo(to);
		email.setAuthentication(user, password);
		if (StringUtils.isEmpty(this.name)) {
			email.setFrom(from);
		}
		else {
			email.setFrom(from, this.name);
		}

		email.setSubject(subject);
		email.setMsg(content);
		if (port == 465) {
			email.setSSLOnConnect(true);
			email.setSslSmtpPort(Integer.toString(port)); // 若启用，设置smtp协议的SSL端口号
		}
		else if (port == 587) {
			// email.
			email.setStartTLSEnabled(true);
			email.setStartTLSRequired(true);
			email.setSmtpPort(port);
			email.setSslSmtpPort(Integer.toString(port)); // 若启用，设置smtp协议的SSL端口号
		}
		else {
			email.setSmtpPort(port);
		}
		email.send();
		return true;
	}

	@Override
	public boolean sendHtml(String to, String subject, String content) throws EmailException {
		HtmlEmail email = new HtmlEmail();
		email.setHostName(host);// 设置使用发电子邮件的邮件服务器
		email.setCharset("UTF-8");
		email.addTo(to);
		email.setAuthentication(user, password);
		if (StringUtils.isEmpty(this.name)) {
			email.setFrom(from);
		}
		else {
			email.setFrom(from, this.name);
		}
		email.setSubject(subject);
		email.setMsg(content);
		// email.setSSLOnConnect(true);
		// email.setSslSmtpPort("465"); // 若启用，设置smtp协议的SSL端口号
		if (port == 465) {
			email.setSSLOnConnect(true);
			email.setSslSmtpPort(Integer.toString(port)); // 若启用，设置smtp协议的SSL端口号
		}
		else if (port == 587) {
			// email.
			email.setStartTLSEnabled(true);
			email.setStartTLSRequired(true);
			email.setSmtpPort(port);
			email.setSslSmtpPort(Integer.toString(port)); // 若启用，设置smtp协议的SSL端口号
		}
		else {
			email.setSmtpPort(port);
		}
		email.send();
		return true;
	}

	@Override
	public boolean sendHtmlByTemplatePath(String to, String subject, String templatePath, Map<String, Object> model) throws EmailException, IOException, TemplateException {
		String content = MailTemplateUtil.render(templatePath, model);
		return this.sendHtml(to, subject, content);
	}
}
