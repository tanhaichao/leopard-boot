package io.leopard.boot.freemarker.method;

import java.util.List;

import org.springframework.stereotype.Component;

import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import io.leopard.boot.freemarker.TemplateVariable;
import io.leopard.boot.freemarker.util.QueryStringBuilder;

/**
 * 设置URL参数
 * 
 * @author 谭海潮
 *
 */
@Component
public class SetUrlParameterTemplateMethod implements TemplateMethodModelEx, TemplateVariable {

	@Override
	public String getKey() {
		return "setUrlParameter";
	}

	@Override
	public Object exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
		String url = args.get(0).toString();
		String name = args.get(1).toString();
		String value = toString(args.get(2));// .toString();
		String newUrl = QueryStringBuilder.buildByUrl(url).setParameter(name, value).toUrl();
		// System.err.println("newUrl:" + newUrl + " name:" + name + " value:" + value);
		return newUrl;
	}

	private static String toString(Object obj) {
		if (obj == null) {
			return null;
		}
		else if (obj instanceof SimpleScalar) {
			return ((SimpleScalar) obj).getAsString();
		}
		else if (obj instanceof SimpleNumber) {
			return ((SimpleNumber) obj).getAsNumber().toString();
		}
		else {
			throw new IllegalArgumentException("未知参数类型[" + obj.getClass().getName() + "].");
		}
	}

}