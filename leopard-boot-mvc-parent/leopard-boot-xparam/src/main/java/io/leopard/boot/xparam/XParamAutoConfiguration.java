package io.leopard.boot.xparam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import io.leopard.boot.xparam.resolver.XParamResolver;

@Configuration
@ComponentScan
@ConditionalOnWebApplication
public class XParamAutoConfiguration extends WebMvcConfigurerAdapter {
	@Autowired
	private List<XParamResolver> xparamResolverList;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.addAll(xparamResolverList);
		super.addArgumentResolvers(argumentResolvers);
	}

	// @Bean
	// public XParamHandlerMethodArgumentResolver resolver() {
	// return new XParamHandlerMethodArgumentResolver();
	// }
	//
	// //
	// // @Bean
	// // public BeanPostProcessorsRegistrar resolver2() {
	// // // new Exception().printStackTrace();
	// // return new BeanPostProcessorsRegistrar();
	// // }
	// //
	// @Bean
	// public PageIdXParam pageId() {
	// return new PageIdXParam();
	// }
	//
	// @Bean
	// public StartXParam start() {
	// return new StartXParam();
	// }
	//
	// @Bean
	// public SizeXParam size() {
	// return new SizeXParam();
	// }

}