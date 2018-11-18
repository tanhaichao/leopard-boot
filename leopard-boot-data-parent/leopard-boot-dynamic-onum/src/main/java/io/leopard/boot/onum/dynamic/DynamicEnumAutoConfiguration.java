package io.leopard.boot.onum.dynamic;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import({ DynamicEnumImportBeanDefinitionRegistrar.class })
public class DynamicEnumAutoConfiguration {

}