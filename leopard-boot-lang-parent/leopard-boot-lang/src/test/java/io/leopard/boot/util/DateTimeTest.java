package io.leopard.boot.util;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class DateTimeTest {

	@Test
	public void getDate() {
		Assert.assertEquals("1970-01-01", DateTime.getDate(0L));

	}

	@Test
	public void test() {
		System.out.println(DateTime.getTime(1453305600000L));
	}

	@Test
	public void getTime() {
		Assert.assertEquals("1970-01-01 08:00:00", DateTime.getTime(0));
		Assert.assertEquals("1970-01-01 08:00:01", DateTime.getTime(1));
		Assert.assertEquals("1970-01-01 07:59:59", DateTime.getTime(-1));

		Assert.assertEquals("1970-01-01 08:00:00", DateTime.getTime(new Date(0)));
		Assert.assertEquals("1970-01-01 08:00:00", DateTime.getTime(new Date(1)));
		Assert.assertEquals("1970-01-01 07:59:59", DateTime.getTime(new Date(-1)));
	}

}