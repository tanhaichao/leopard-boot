package io.leopard.redis.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.leopard.redis.Redis;
import io.leopard.redis.RedisImpl;

@Configuration
// @ConditionalOnProperty(prefix = "app.redis", name = "host", matchIfMissing = false)
// @EnableConfigurationProperties(RedisProperties.class)
@Import({ RedisRegistrar.class })
public class RedisAutoConfiguration {

	@Bean(name = "redis", initMethod = "init", destroyMethod = "destroy")
	public Redis redis(RedisProperties redisConfig) {
		String server = redisConfig.parseServer();
		RedisImpl redis = new RedisImpl();
		redis.setServer(server);
		if (redisConfig.getMaxActive() != null) {
			redis.setMaxActive(redisConfig.getMaxActive());
		}
		return redis;
	}
}