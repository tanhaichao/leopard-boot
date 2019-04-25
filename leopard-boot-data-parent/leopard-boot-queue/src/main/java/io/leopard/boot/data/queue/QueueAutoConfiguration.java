package io.leopard.boot.data.queue;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ QueueRegistrar.class })
public class QueueAutoConfiguration {

}