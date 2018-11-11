package io.leopard.boot.freemarker;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LeopardFreemarkerConfiguration {
	@Autowired
	private freemarker.template.Configuration configuration;

	@Autowired
	private List<TemplateVariable> templateVariableList;

	@PostConstruct
	public void init() throws Exception {
		for (TemplateVariable variable : templateVariableList) {
			configuration.setSharedVariable(variable.getKey(), variable);
		}

	}
}
