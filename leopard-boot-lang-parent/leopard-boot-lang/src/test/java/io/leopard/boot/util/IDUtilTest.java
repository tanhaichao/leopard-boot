package io.leopard.boot.util;

import java.util.ArrayList;
import java.util.List;

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

	@Test
	public void checkUniqueIdIgnoreEmpty() {
		List<Bean> beanList = new ArrayList<>();
		beanList.add(new Bean("id1", "name1"));
		beanList.add(new Bean("id2", "name2"));
		beanList.add(new Bean("id2", "name2"));
		beanList.add(new Bean("", "name2"));
		beanList.add(new Bean("", "name2"));

		System.out.println("beanList:" + beanList);
		IDUtil.checkUniqueIdIgnoreEmpty(beanList, Bean::getXxxId, "ID必须唯一");
		System.out.println("beanList:" + beanList);
	}

	public static class Bean {
		private String xxxId;

		private String name;

		public Bean(String xxxId, String name) {
			this.xxxId = xxxId;
			this.name = name;
		}

		public String getXxxId() {
			return xxxId;
		}

		public void setXxxId(String xxxId) {
			this.xxxId = xxxId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Bean [xxxId=" + xxxId + ", name=" + name + "]";
		}

	}

}