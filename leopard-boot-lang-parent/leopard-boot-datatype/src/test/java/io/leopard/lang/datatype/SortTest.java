package io.leopard.lang.datatype;

import org.junit.Assert;
import org.junit.Test;

public class SortTest {

	@Test
	public void Sort() {
		Sort sort = new Sort();
		sort.setSort("+id");
		Assert.assertEquals("id", sort.getFieldName());
		Assert.assertFalse(sort.isAscending());
		Assert.assertTrue(sort.isDescending());

		sort.setSort("-id");
		Assert.assertEquals("id", sort.getFieldName());
		Assert.assertTrue(sort.isAscending());
		Assert.assertFalse(sort.isDescending());

	}

}