package io.leopard.web.mvc.json.common;

import java.util.Date;

import org.junit.Test;

import io.leopard.boot.util.DateUtil;

public class AgeJsonSerializerTest {

	@Test
	public void getAge() {
		Date birth = DateUtil.toDate("1990-07-08 00:00:00");
		int age = AgeJsonSerializer.getAge(birth);
		System.out.println("age:" + age);
	}

}