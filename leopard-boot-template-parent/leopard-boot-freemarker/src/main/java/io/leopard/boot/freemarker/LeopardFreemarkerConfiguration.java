package io.leopard.boot.freemarker;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import io.leopard.boot.freemarker.annotations.FunctionBean;

@Configuration
public class LeopardFreemarkerConfiguration {
	@Autowired
	private freemarker.template.Configuration configuration;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private List<TemplateVariable> templateVariableList;

	@PostConstruct
	public void init() throws Exception {
		for (TemplateVariable variable : templateVariableList) {
			configuration.setSharedVariable(variable.getKey(), variable);
		}

		{
			Map<String, Object> map = this.applicationContext.getBeansWithAnnotation(FunctionBean.class);
			for (String key : map.keySet()) {
				configuration.setSharedVariable(key, map.get(key));
			}
		}
	}
}
