package io.leopard.jdbc.autoconfigure;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.JdbcMysqlImpl;
import io.leopard.jdbc.datasource.MysqlDsnDataSource;
import io.leopard.jdbc.transaction.LeopardTransactionManager;

@Configuration
@ConditionalOnProperty(prefix = "spring.datasource", name = "url", matchIfMissing = false)
@EnableConfigurationProperties(JdbcProperties.class)
public class JdbcAutoConfiguration {

	@Bean(name = "dataSource", initMethod = "init", destroyMethod = "destroy")
	@Primary
	public DataSource dataSource(JdbcProperties jdbcConfig) {
		int idleConnectionTestPeriod = 60;
		String user = jdbcConfig.getUsername();
		String password = jdbcConfig.getPassword();
		String url = jdbcConfig.getUrl();

		MysqlDsnDataSource dataSource = new MysqlDsnDataSource();
		dataSource.setMaxPoolSize(15);
		dataSource.setUser(user);
		dataSource.setPassword(password);
		dataSource.setUrl(url);
		dataSource.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
		return dataSource;
	}

	@Bean
	@Primary
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		LeopardTransactionManager transactionManager = new LeopardTransactionManager();
		transactionManager.setDataSource(dataSource);
		return transactionManager;
	}

	@Bean(name = "jdbc", destroyMethod = "destroy")
	@Primary
	public Jdbc jdbc(DataSource dataSource) {
		JdbcMysqlImpl jdbc = new JdbcMysqlImpl();
		jdbc.setDataSource(dataSource);
		return jdbc;
	}

}