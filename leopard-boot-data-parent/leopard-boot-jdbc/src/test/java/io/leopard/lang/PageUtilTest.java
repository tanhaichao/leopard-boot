package io.leopard.lang;

import org.junit.Assert;
import org.junit.Test;

public class PageUtilTest {

	@Test
	public void getPageId() {
		Assert.assertEquals(1, PageUtil.getPageId(0, 10));
		Assert.assertEquals(1, PageUtil.getPageId(9, 10));
		Assert.assertEquals(2, PageUtil.getPageId(10, 10));
	}

}