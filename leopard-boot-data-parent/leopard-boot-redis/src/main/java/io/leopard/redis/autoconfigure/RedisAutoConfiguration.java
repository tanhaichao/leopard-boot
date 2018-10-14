package io.leopard.redis.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.leopard.redis.Redis;
import io.leopard.redis.RedisImpl;

@Configuration
// @ConfigurationProperties(prefix = "zhaotong.redis")
@ConditionalOnProperty(prefix = "app.redis", name = "host", matchIfMissing = false)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisAutoConfiguration {

	// @Bean
	// @ConfigurationProperties(prefix = "app.redis")
	// public RedisProperties config() {
	// DataSourceProperties ddd;
	// RedisProperties config = new RedisProperties();
	// return config;
	// }

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