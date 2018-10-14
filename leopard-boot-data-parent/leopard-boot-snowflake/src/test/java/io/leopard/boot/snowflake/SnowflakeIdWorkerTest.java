package io.leopard.boot.snowflake;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class SnowflakeIdWorkerTest {

	protected Log log = LogFactory.getLog(this.getClass());

	@Test
	public void test() {
		this.productId(1, 1, 10000000);
	}

	public void productId(int dataCenterId, int workerId, int n) {
		SnowflakeIdWorker idWorker = new SnowflakeIdWorker(workerId, dataCenterId);
		SnowflakeIdWorker idWorker2 = new SnowflakeIdWorker(workerId + 1, dataCenterId);
		Set<Long> setOne = new HashSet<>();
		Set<Long> setTow = new HashSet<>();
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			setOne.add(idWorker.nextId());// 加入set
		}
		long end1 = System.currentTimeMillis() - start;
		log.info("第一批ID预计生成{" + n + "}个,实际生成{" + setOne.size() + "}个<<<<*>>>>共耗时:{" + end1 + "}");
		for (int i = 0; i < n; i++) {
			setTow.add(idWorker2.nextId());// 加入set
		}
		long end2 = System.currentTimeMillis() - start;

		log.info("第二批ID预计生成{" + n + "}个,实际生成{" + setTow.size() + "}个<<<<*>>>>共耗时:{" + end2 + "}");
		setOne.addAll(setTow);
		//
		log.info("合并总计生成ID个数:{" + setOne.size() + "}");
	}

	@Test
	public void nextId() {
		SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1, 1);
		long id = idWorker.nextId();
		System.out.println("id:" + id);
	}

}