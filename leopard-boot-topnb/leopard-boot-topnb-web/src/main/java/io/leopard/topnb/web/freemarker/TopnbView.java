package io.leopard.topnb.web.freemarker;

import org.springframework.web.servlet.ModelAndView;

public class TopnbView extends ModelAndView {

	public TopnbView(String templateName) {
		super("/topnb/topnb");
		super.addObject("template_name", templateName);
	}

	// @Override
	// public List<TemplateVariable> getVariables() {
	// List<TemplateVariable> list = new ArrayList<TemplateVariable>();
	// list.add(new TimeTemplateMethod());
	// list.add(new AvgTimeTemplateMethod());
	// // list.add(new BodyTemplateDirective());
	//
	// list.add(new MenuTemplateDirective());
	// list.add(new ServerInfoTemplateDirective());
	// list.add(new ReplaceParamTemplateMethod());
	//
	// return list;
	// }
}
