package io.leopard.boot.util;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest {

	@Test
	public void uuid() {
		System.out.println(StringUtil.uuid().length());
	}

	@Test
	public void isChinese() {
		Assert.assertTrue(StringUtil.isChinese('中'));
		Assert.assertFalse(StringUtil.isChinese(','));
	}

}