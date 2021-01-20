package io.leopard.boot.snowflake;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SnowflakeUtil {

	// workerId 机器ID(0-31)

	// private static SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1);// TODO 不同机器不同编号
	// private static SequenceIdWorker sequenceIdWorker = new SequenceIdWorker(1);// TODO 不同机器不同编号

	private static SnowflakeIdService snowflakeIdService;

	@Autowired
	public void setSnowflakeIdService(SnowflakeIdService snowflakeIdService) {
		SnowflakeUtil.snowflakeIdService = snowflakeIdService;
	}

	public static long generateId() {
		return snowflakeIdService.generateId();
	}

	/**
	 * 生成53位的的ID
	 * 
	 * @return
	 */
	public static long generate53BitId() {
		return snowflakeIdService.generate53BitId();
	}
}
