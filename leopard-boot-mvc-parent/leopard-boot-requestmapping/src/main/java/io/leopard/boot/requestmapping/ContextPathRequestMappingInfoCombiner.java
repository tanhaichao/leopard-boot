package io.leopard.boot.requestmapping;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

@Component
public class ContextPathRequestMappingInfoCombiner implements RequestMappingInfoCombiner {
	@Value("${leopard.context-path:}") // 默认为空
	private String contextPath;

	@Autowired(required = false)
	private List<ContextPathSwitchgear> contextPathSwitchgears;

	@Override
	public RequestMappingInfo combine(RequestMappingInfo.BuilderConfiguration options, RequestMappingInfo info, Method method, Class<?> handlerType) {
		boolean isEnableContextPath = isEnableContextPath(method, handlerType);
		if (!isEnableContextPath) {
			return null;
		}
		RequestMappingInfo context = RequestMappingInfo.paths(contextPath).options(options).build();
		return context.combine(info);
	}

	protected boolean isEnableContextPath(Method method, Class<?> handlerType) {
		if (StringUtils.isEmpty(contextPath)) {
			return false;
		}
		if (contextPathSwitchgears != null) {
			for (ContextPathSwitchgear switchgear : contextPathSwitchgears) {
				Boolean enable = switchgear.isEnableContextPath(method, handlerType);
				System.err.println("switchgear:" + switchgear.getClass().getName() + " " + enable);
				if (enable != null) {
					return enable;
				}
			}
		}
		return true;
	}
}
