package io.leopard.boot.web.test;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "debug", havingValue = "true")
@ComponentScan
public class WebTestAutoConfiguration {

	// @Bean
	// public XparamController XparamController() {
	// return new XparamController();
	// }
	//
	// @Bean
	// public RequestMappingController RequestMappingController() {
	// return new RequestMappingController();
	// }
}