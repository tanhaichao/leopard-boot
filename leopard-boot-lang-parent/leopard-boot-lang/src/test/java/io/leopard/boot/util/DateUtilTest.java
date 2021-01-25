package io.leopard.boot.util;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import io.leopard.lang.datatype.Month;
import io.leopard.lang.datatype.OnlyDate;

public class DateUtilTest {

	@Test
	public void addYear() {
		Date date = DateUtil.addYear(new Date(), 10);
		System.out.println("date:" + DateTime.getTime(date));
	}

	@Test
	public void test() {
		long time = 1604740749000L;
		Date date = new Date(1604740749000L);
		System.out.println("date:" + DateTime.getTime(date));

	}

	@Test
	public void getLastTimeOfMonth() {
		System.out.println("lastTime:" + DateTime.getTime(DateUtil.getLastTimeOfMonth(new OnlyDate("2020-01-30"))));
		System.out.println("lastTime:" + DateTime.getTime(DateUtil.getLastTimeOfMonth(new Month("2020-02"))));
		System.out.println("lastTime:" + DateTime.getTime(DateUtil.getLastTimeOfMonth(new Month("2020-03"))));
		System.out.println("lastTime:" + DateTime.getTime(DateUtil.getLastTimeOfMonth(new Month("2020-04"))));
		System.out.println("lastTime:" + DateTime.getTime(DateUtil.getLastTimeOfMonth(new Month("2020-05"))));
		System.out.println("lastTime:" + DateTime.getTime(DateUtil.getLastTimeOfMonth(new Month("2020-06"))));
		System.out.println("lastTime:" + DateTime.getTime(DateUtil.getLastTimeOfMonth(new Month("2020-07"))));
		System.out.println("lastTime:" + DateTime.getTime(DateUtil.getLastTimeOfMonth(new Month("2020-08"))));
		System.out.println("lastTime:" + DateTime.getTime(DateUtil.getLastTimeOfMonth(new Month("2020-09"))));
	}

	@Test
	public void toDate() {
		Assert.assertEquals("1970-01-01 08:00:00", DateTime.getTime(DateUtil.toDate("1970-01-01 08:00:00")));
		Assert.assertEquals("1970-01-01 07:00:00", DateTime.getTime(DateUtil.toDate("1970-01-01 07:00:00")));
	}

}