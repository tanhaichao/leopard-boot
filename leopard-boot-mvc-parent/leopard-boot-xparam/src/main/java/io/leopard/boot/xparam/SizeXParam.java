package io.leopard.boot.xparam;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;

/**
 * 获取分页记录条数
 * 
 * @author 阿海
 * 
 */
@Service
public class SizeXParam implements XParam {

	@Override
	public Object getValue(HttpServletRequest request, MethodParameter parameter) {
		int size = XParamUtil.toInt(request.getParameter("size"));
		if (size <= 0) {
			size = this.getDefaultSizeByAnnotation(parameter);
		}
		if (size <= 0) {
			size = 10;
		}
		// request.setAttribute("paging_size", size);// TODO ahai 耦合
		return size;
	}

	/**
	 * 根据注解获取默认分页大小
	 * 
	 * @param parameter
	 * @return
	 */
	protected int getDefaultSizeByAnnotation(MethodParameter parameter) {
		// TODO ahai 增加缓存
		if (this.getKey().equalsIgnoreCase(parameter.getParameterName())) {
			None none = parameter.getParameterAnnotation(None.class);
			if (none == null) {
				return 0;
			}
			else {
				return Integer.parseInt(none.value());
			}
		}

		String[] names = XParamUtil.getParameterNames(parameter);
		Annotation[][] annotations = parameter.getMethod().getParameterAnnotations();
		for (int i = 0; i < names.length; i++) {
			if (this.getKey().equalsIgnoreCase(names[i])) {
				None none = find(annotations[i]);
				if (none == null) {
					return 0;
				}
				else {
					return Integer.parseInt(none.value());
				}
			}
		}
		return 0;
	}

	protected None find(Annotation[] annotations) {
		for (Annotation anno : annotations) {
			if (anno instanceof None) {
				return (None) anno;
			}
		}
		return null;
	}

	@Override
	public String getKey() {
		return "size";
	}

	// @Override
	// public void override(XParam xparam) {
	//
	// }

}
