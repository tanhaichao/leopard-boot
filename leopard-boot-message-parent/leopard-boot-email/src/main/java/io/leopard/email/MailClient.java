package io.leopard.email;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.mail.EmailException;

import freemarker.template.TemplateException;

/**
 * 邮件发送器
 * 
 * @author 谭海潮
 *
 */
public interface MailClient {

	boolean sendText(String to, String subject, String content) throws EmailException;

	boolean sendHtml(String to, String subject, String content) throws EmailException;

	boolean sendHtmlByTemplatePath(String to, String subject, String templatePath, Map<String, Object> model) throws EmailException, IOException, TemplateException;
}
