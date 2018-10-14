package io.leopard.boot.test.datasource;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.JdbcMysqlImpl;
import io.leopard.jdbc.autoconfigure.JdbcProperties;
import io.leopard.jdbc.datasource.MysqlDsnDataSource;
import io.leopard.jdbc.transaction.LeopardTransactionManager;
import io.leopard.jvmdns.JvmdnsEnvironmentPostProcessor;
import io.leopard.redis.Redis;
import io.leopard.redis.util.RedisFactory;

/**
 * 项目数据源配置
 * 
 * @author 谭海潮
 *
 */
@Configuration
@ConditionalOnMissingClass(value = "io.leopard.boot.LeopardApplication")
public class ProjectDatasourceAutoConfiguration {

	// FIXME 项目地址要改成动态识别
	private File projectServiceModuleDir = new File("/work/jiuniu/scp/scp-service");

	@Bean
	public JdbcProperties testJdbcProperties() throws IOException {
		this.updateJvmdns();

		File file = new File(projectServiceModuleDir, "src/main/resources/application.properties");
		Resource resource = new FileSystemResource(file);
		Properties properties = PropertiesLoaderUtils.loadProperties(resource);

		String url = (String) properties.get("spring.datasource.url");
		String username = (String) properties.get("spring.datasource.username");
		String password = (String) properties.get("spring.datasource.password");

		if (StringUtils.isEmpty(url)) {
			throw new RuntimeException("读取不到spring.datasource.url.");
		}

		// FIXME 这里要改成读取项目的信息
		JdbcProperties config = new JdbcProperties();
		config.setUsername(username);
		config.setPassword(password);
		config.setUrl(url);
		return config;
	}

	protected void updateJvmdns() {
		File file = new File(projectServiceModuleDir, "src/main/resources/jvmdns.properties");
		Resource resource = new FileSystemResource(file);

		JvmdnsEnvironmentPostProcessor.load(resource);
	}

	@Bean(name = "dataSource", initMethod = "init", destroyMethod = "destroy")
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
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		LeopardTransactionManager transactionManager = new LeopardTransactionManager();
		transactionManager.setDataSource(dataSource);
		return transactionManager;
	}

	@Bean(name = "jdbc", destroyMethod = "destroy")
	public Jdbc jdbc(DataSource dataSource) {
		JdbcMysqlImpl jdbc = new JdbcMysqlImpl();
		jdbc.setDataSource(dataSource);
		return jdbc;
	}

	@Bean
	@ConditionalOnMissingClass(value = "io.leopard.boot.LeopardApplication")
	public Redis redis() throws IOException {// TODO 名称要改成sessionRedis
		File file = new File(projectServiceModuleDir, "src/main/resources/application.properties");
		Resource resource = new FileSystemResource(file);
		Properties properties = PropertiesLoaderUtils.loadProperties(resource);

		String host = (String) properties.get("app.redis.host");
		int port = Integer.parseInt((String) properties.get("app.redis.port"));
		String password = (String) properties.get("app.redis.password");

		String server = host + ":" + port;
		if (!StringUtils.isEmpty(password)) {
			server += ":" + password;
		}
		Redis redis = RedisFactory.create(server);
		return redis;
	}
}
