package io.leopard.boot.util;

import java.util.Date;

import org.junit.Test;

public class DateUtilTest {

	@Test
	public void addYear() {
		Date date = DateUtil.addYear(10);
		System.out.println("date:" + DateTime.getTime(date));
	}

}