package io.xiaoniu.boot.mask;

import org.junit.Test;

public class AESUtilTest {
	@Test
	public void test2() throws Exception {
		String pkey = "12345678901234567890123456789012";
		System.out.println("mobile:" + AESUtil.encode("13912341111111111112314", pkey));
		System.out.println("email:" + AESUtil.encode("你好吗哈哈哈哈哈你好吗哈哈哈哈哈", pkey));
	}

	@Test
	public void test() throws Exception {
		// 明文
		String content = "qq245635595";
		// 密匙
		String pkey = "wwwwwwwwwwwwwww1wwwwwwwwwwwwwww1";
		pkey = "12345678901234567890123456789012";

		System.out.println("待加密报文:" + content);
		System.out.println("密匙:" + pkey + "长度:" + pkey.length());
		String aesEncryptStr = AESUtil.encode(content, pkey);
		System.out.println("加密报文:" + aesEncryptStr);
		String aesDecodeStr = AESUtil.decode(aesEncryptStr, pkey);
		System.out.println("解密报文:" + aesDecodeStr);
		System.out.println("加解密前后内容是否相等:" + aesDecodeStr.equals(content));

	}

}