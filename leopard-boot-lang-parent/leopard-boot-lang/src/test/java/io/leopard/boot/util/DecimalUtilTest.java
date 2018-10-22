package io.leopard.boot.util;

import org.junit.Test;

public class DecimalUtilTest {

	@Test
	public void isSecure() {
		double max = Math.pow(10, 6);
		System.out.println("max:" + max);
		DecimalUtil.isSecure(100.0);
	}

	@Test
	public void subtract() {
		double num = DecimalUtil.subtract(1, 2);
		System.out.println("num:" + num);
	}

}