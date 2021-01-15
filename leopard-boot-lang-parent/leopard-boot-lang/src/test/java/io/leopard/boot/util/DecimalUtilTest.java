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

	@Test
	public void toIntFee() {
		DecimalUtil.toIntFee(100.01, 2);
		DecimalUtil.toIntFee(100.012, 3);
		DecimalUtil.toIntFee(100.012, 2);
	}

	@Test
	public void percent() {
		double percent = DecimalUtil.percent(12.51, 25.24);
		System.err.println("percent:" + percent);
	}

}