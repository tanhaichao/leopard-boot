package io.leopard.boot.elasticsearch;

import org.junit.Test;

public class ScoreTest {

	protected double log(int displayOrder) {
		// 0.9767073907678475
		return Math.log(1 + displayOrder) / 22d;
		// return Math.log(1 + displayOrder) * 100 / 22d;
	}

	@Test
	public void displayOrder() {
		System.out.println("num:" + log(111111));
		System.out.println("num:" + log(Integer.MAX_VALUE - 1));
	}
}
