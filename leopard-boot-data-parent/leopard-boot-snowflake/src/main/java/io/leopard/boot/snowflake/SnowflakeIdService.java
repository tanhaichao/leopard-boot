package io.leopard.boot.snowflake;

public interface SnowflakeIdService {

	/**
	 * 生成ID
	 * 
	 * @return
	 */
	long generateId();

	long generate53BitId();

	/**
	 * 获取workerId
	 * 
	 * @return
	 */
	int getWorkerId();
}
