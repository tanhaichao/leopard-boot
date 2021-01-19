package io.leopard.boot.snowflake;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class SequenceIdWorkerTest {

	@Test
	public void nextId() throws InterruptedException {
		Set<Long> idSet = Collections.synchronizedSet(new HashSet<>());
		SequenceIdWorker idWorker = new SequenceIdWorker(1);
		for (int i = 0; i < 10; i++) {
			this.startThread(idWorker, idSet, 10000);
		}
		Thread.sleep(1000 * 3);
		System.out.println("idSet:" + idSet.size());

	}

	private void startThread(SequenceIdWorker idWorker, Set<Long> idSet, int size) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				for (int i = 0; i < size; i++) {
					long id = idWorker.nextId();
					idSet.add(id);
				}
			}
		};
		thread.start();
	}
}