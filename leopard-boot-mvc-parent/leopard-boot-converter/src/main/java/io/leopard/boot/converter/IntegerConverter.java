package io.leopard.boot.converter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IntegerConverter implements Converter<String, Integer> {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public Integer convert(String source) {
		if (StringUtils.isEmpty(source)) {
			return 0;
		}
		return Integer.parseInt(source);
	}
}
