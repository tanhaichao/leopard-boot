package io.leopard.boot.snowflake;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.leopard.redis.Redis;

@Component
public class SnowflakeIdServiceImpl implements SnowflakeIdService {

	// private SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1);// TODO

	private SnowflakeIdWorker idWorker;
	private SequenceIdWorker sequenceIdWorker;

	@Autowired
	private Redis redis;

	@PostConstruct
	public void init() {
		int workerIdMax = 15;
		int workerId = this.getWorkerId(workerIdMax);

		idWorker = new SnowflakeIdWorker(workerId);
		sequenceIdWorker = new SequenceIdWorker(workerId);
	}

	protected int getWorkerId(int workerIdMax) {
		long lastWorkerId = this.getLastWorkerId();
		int workerId = (int) (lastWorkerId % workerIdMax);
		return workerId;
	}

	protected long getLastWorkerId() {
		long lastWorkerId = redis.incr("snowflake_worker_id");
		return lastWorkerId;
	}

	@Override
	public long generateId() {
		return idWorker.nextId();
	}

	/**
	 * 生成53位的的ID
	 * 
	 * @return
	 */
	@Override
	public long generate53BitId() {
		return sequenceIdWorker.nextId();
	}
}
