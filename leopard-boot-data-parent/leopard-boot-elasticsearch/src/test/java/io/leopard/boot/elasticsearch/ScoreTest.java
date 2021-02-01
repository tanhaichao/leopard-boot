package io.leopard.boot.elasticsearch;

import org.junit.Test;

public class ScoreTest {

	protected double log(int displayOrder) {
		// 0.9767073907678475
		// return Math.log(1 + displayOrder) / 22d;
		return Math.log(1 + displayOrder) * 1000;
		// return (displayOrder / 100d);
	}

	@Test
	public void displayOrder() {
		// double displayOrder2 = Math.log(1 + packet.getDisplayOrder()) * 100 / 22d;

		System.out.println("num:" + log(0));
		System.out.println("num:" + log(996));
		System.out.println("num:" + log(997));
		System.out.println("num:" + log(998));
		System.out.println("num:" + log(1998));
		// System.out.println("num:" + log(Integer.MAX_VALUE - 1));
	}

	@Test
	public void salesQuantity() {
		System.out.println("销量\t\tsqrt档\t\tlog档");
		for (int i = 0; i < 10000000; i++) {
			System.out.println(i + "\t\t" + ((int) Math.sqrt(i)) + "\t\t" + ((int) Math.log(i)));
			i += i;
		}
	}
}
