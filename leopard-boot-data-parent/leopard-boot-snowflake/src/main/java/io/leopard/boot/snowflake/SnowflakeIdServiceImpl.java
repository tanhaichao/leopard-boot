package io.leopard.boot.snowflake;

public class SnowflakeIdServiceImpl implements SnowflakeIdService {

	private SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1);// TODO

	@Override
	public long generateId() {
		return idWorker.nextId();
	}

}
