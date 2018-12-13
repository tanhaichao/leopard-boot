package io.leopard.boot.session;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@ComponentScan
@EnableRedisHttpSession
public class SessionAutoConfiguration {

}