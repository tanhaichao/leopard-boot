package io.leopard.boot.snowflake;

public class SnowflakeUtil {

	private static SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1);// TODO

	public static long generateId() {
		return idWorker.nextId();
	}
}
