package io.leopard.boot.converter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class ConverterAutoConfiguration {
	// <!-- 自定义数据类型 -->
	// <property name="converters">
	// <list>
	// <bean class="io.leopard.web.editor.DateConverter" />
	// <bean class="io.leopard.web.editor.OnlyDateConverter" />
	// <bean class="io.leopard.web.editor.MonthConverter" />
	// <bean class="io.leopard.web.editor.StringToEnumConverterFactory" />
	// </list>
	// </property>

}