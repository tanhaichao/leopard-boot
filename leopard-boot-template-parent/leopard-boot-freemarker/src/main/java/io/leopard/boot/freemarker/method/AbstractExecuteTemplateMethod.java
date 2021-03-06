package io.leopard.boot.freemarker.method;

import java.lang.reflect.Method;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import freemarker.ext.beans.StringModel;
import freemarker.template.SimpleNumber;

public abstract class AbstractExecuteTemplateMethod extends AbstractTemplateMethod {

	@Override
	public Object execute(HttpServletRequest request, Object... args) throws Exception {
		Method method = this.findExecuteMethod();
		if (method == null) {
			throw new RuntimeException("找不到execute方法.");
		}
		Class<?>[] types = method.getParameterTypes();
		if (types.length != args.length) {
			throw new RuntimeException("参数数量不匹配[" + types.length + " " + args.length + "].");
		}
		Object[] methodArgs = new Object[types.length];
		for (int i = 0; i < types.length; i++) {
			if (args[i] == null) {
				methodArgs[i] = null;
			}
			else if (types[i].equals(String.class)) {
				methodArgs[i] = args[i];
			}
			else if (types[i].equals(int.class) || types[i].equals(Integer.class)) {
				if (args[i] instanceof BigDecimal) {
					methodArgs[i] = ((BigDecimal) args[i]).intValue();
				}
				else if (args[i] instanceof SimpleNumber) {
					methodArgs[i] = ((SimpleNumber) args[i]).getAsNumber().intValue();
				}
				else {
					methodArgs[i] = args[i];// ((SimpleNumber) args[i]).getAsNumber().intValue();
				}
			}
			else if (types[i].equals(long.class) || types[i].equals(Long.class)) {
				// methodArgs[i] = args[i];// ((SimpleNumber) args[i]).getAsNumber();
				if (args[i] instanceof BigDecimal) {
					methodArgs[i] = ((BigDecimal) args[i]).longValue();
				}
				else if (args[i] instanceof SimpleNumber) {
					methodArgs[i] = ((SimpleNumber) args[i]).getAsNumber().longValue();
				}
				else {
					methodArgs[i] = args[i];// ((SimpleNumber) args[i]).getAsNumber().intValue();
				}
			}
			else if (types[i].equals(Object.class)) {
				if (args[i] instanceof String) {
					methodArgs[i] = args[i];
				}
				else if (args[i] instanceof BigDecimal) {
					throw new RuntimeException("暂时不支持该类型.");
				}
				else if (args[i] instanceof SimpleNumber) {
					throw new RuntimeException("暂时不支持该类型.");
				}
				else {
					methodArgs[i] = ((StringModel) args[i]).getWrappedObject();
				}
			}
			else {
				// TODO 什么情况下会来到这里?
				// logger.warn("types[i]:" + types[i].getName());

				methodArgs[i] = ((StringModel) args[i]).getWrappedObject();
				// logger.warn("types[i]:" + types[i].getName() + " methodArgs[i]:" + methodArgs[i].getClass().getName());
				// System.err.println("methodArgs[i]:" + methodArgs[i].getClass().getName());
			}
		}
		try {
			return method.invoke(this, methodArgs);
		}
		catch (Exception e) {
			logger.error("url:" + request.getRequestURL().toString() + " method:" + method.toGenericString() + " methodArgs:" + methodArgs.length + " args:" + args.length);
			throw e;
		}
	}

	protected Method findExecuteMethod() {
		Method[] methods = this.getClass().getMethods();
		for (Method method : methods) {
			if ("execute".equals(method.getName())) {
				return method;
			}
		}
		return null;
	}

}
