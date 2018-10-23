package io.leopard.boot.util;

import org.junit.Assert;
import org.junit.Test;

public class IDUtilTest {

	@Test
	public void isEmptyId() {
		Assert.assertTrue(IDUtil.isEmptyId(null));
		Assert.assertTrue(IDUtil.isEmptyId(0));
		Assert.assertTrue(IDUtil.isEmptyId(-1));
		Assert.assertTrue(IDUtil.isEmptyId(0L));
		Assert.assertFalse(IDUtil.isEmptyId(1L));
		Assert.assertTrue(IDUtil.isEmptyId(""));
		Assert.assertFalse(IDUtil.isEmptyId("a"));
	}

}