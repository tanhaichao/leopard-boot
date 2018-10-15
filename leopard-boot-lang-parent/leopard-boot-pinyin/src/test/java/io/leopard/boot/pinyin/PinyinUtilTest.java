package io.leopard.boot.pinyin;

import org.junit.Assert;
import org.junit.Test;

public class PinyinUtilTest {

	@Test
	public void isValidCharacters() {
		Assert.assertTrue(PinyinUtil.isValidCharacters("中文"));
		Assert.assertFalse(PinyinUtil.isValidCharacters("中文2"));
		Assert.assertFalse(PinyinUtil.isValidCharacters("中文("));
		Assert.assertTrue(PinyinUtil.isValidCharacters("中文a"));
	}

}