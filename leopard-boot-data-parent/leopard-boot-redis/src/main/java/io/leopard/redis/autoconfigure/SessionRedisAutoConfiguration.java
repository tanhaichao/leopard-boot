package io.leopard.redis.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.leopard.redis.Redis;
import io.leopard.redis.RedisImpl;

@Configuration
@ConditionalOnProperty(prefix = "session.redis", name = "host", matchIfMissing = false)
@EnableConfigurationProperties(RedisProperties.class)
public class SessionRedisAutoConfiguration {

	@Bean(name = "sessionRedis", initMethod = "init", destroyMethod = "destroy")
	public Redis sessionRedis(RedisProperties redisConfig) {
		String server = redisConfig.parseServer();
		RedisImpl redis = new RedisImpl();
		redis.setServer(server);
		if (redisConfig.getMaxActive() != null) {
			redis.setMaxActive(redisConfig.getMaxActive());
		}
		return redis;
	}
}