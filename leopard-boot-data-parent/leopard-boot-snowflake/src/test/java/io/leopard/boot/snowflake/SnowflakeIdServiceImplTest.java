package io.leopard.boot.snowflake;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SnowflakeIdServiceImplTest {

	@Autowired
	private SnowflakeIdServiceImpl snowflakeIdService = new SnowflakeIdServiceImpl();

	@Test
	public void generateId() {
		long id = snowflakeIdService.generateId();
		System.out.println("id:" + id);
	}

}