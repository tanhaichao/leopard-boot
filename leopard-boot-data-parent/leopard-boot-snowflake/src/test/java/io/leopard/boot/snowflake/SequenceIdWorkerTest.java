package io.leopard.boot.snowflake;

import org.junit.Test;

public class SequenceIdWorkerTest {

	@Test
	public void nextId() {
		SequenceIdWorker idWorker = new SequenceIdWorker(1);
		for (int i = 0; i < 100; i++) {
			long id = idWorker.nextId();
			System.out.println(id);

		}
	}

}