package io.leopard.topnb.web.freemarker;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import io.leopard.boot.freemarker.TemplateVariable;

/**
 * 消息Tab.
 * 
 * @author 阿海
 *
 */
@Component
public class ServerInfoTemplateDirective implements TemplateDirectiveModel, TemplateVariable {

	private static Date serverStartTime = new Date();

	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String html = "当前服务器IP:127.0.0.1  服务启动:" + serverStartTime.toLocaleString();
		env.getOut().write(html);
	}

	@Override
	public String getKey() {
		return "serverInfo";
	}

}
