package io.leopard.lang.datatype;

import org.junit.Test;

public class MonthTest {

	@Test
	public void Month() {
		Month month = new Month();
		System.out.println("time:" + month.getTime() + " month:" + month);
	}

	@Test
	public void addMonth() {
		Month month = Month.addMonth(-1);
		System.out.println("time:" + month.getTime() + " month:" + month);
	}

}