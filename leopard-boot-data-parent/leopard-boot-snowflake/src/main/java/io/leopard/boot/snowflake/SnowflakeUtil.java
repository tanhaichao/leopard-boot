package io.leopard.boot.snowflake;

public class SnowflakeUtil {

	// workerId 机器ID(0-31)

	private static SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1);// TODO 不同机器不同编号

	public static long generateId() {
		return idWorker.nextId();
	}

	private static SequenceIdWorker sequenceIdWorker = new SequenceIdWorker(1);// TODO 不同机器不同编号

	/**
	 * 生成53位的的ID
	 * 
	 * @return
	 */
	public static long generate53BitId() {
		return sequenceIdWorker.nextId();
	}
}
