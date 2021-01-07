package io.xiaoniu.boot.mask;

import java.io.UnsupportedEncodingException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AESUtil {

	/*** 默认向量常量 **/
	public static final String IV = "0123456789123456";

	// private final static Log logger = LogFactory.getLog(AESUtil.class);

	private static byte[] IV_BYTES;
	/**
	 * 使用PKCS7Padding填充必须添加一个支持PKCS7Padding的Provider 类加载的时候就判断是否已经有支持256位的Provider,如果没有则添加进去
	 */
	static {
		try {
			IV_BYTES = IV.getBytes("UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
			Security.addProvider(new BouncyCastleProvider());
		}
	}

	/**
	 * 加密 128位
	 *
	 * @param content 需要加密的原内容
	 * @param pkey 密匙
	 * @return
	 */
	public static byte[] encodeForBytes(String content, String pkey) {
		byte[] keyBytes = pkey.getBytes();
		if (keyBytes.length != 32) {
			throw new IllegalArgumentException("key长度不是32位[" + keyBytes + "]");
		}
		try {
			// SecretKey secretKey = generateKey(pkey);
			// byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec skey = new SecretKeySpec(keyBytes, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");// "算法/加密/填充"
			IvParameterSpec iv = new IvParameterSpec(IV_BYTES);
			cipher.init(Cipher.ENCRYPT_MODE, skey, iv);// 初始化加密器
			byte[] encrypted = cipher.doFinal(content.getBytes("UTF-8"));
			return encrypted; // 加密
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * @param content 加密前原内容
	 * @param pkey 长度为16个字符,128位
	 * @return base64EncodeStr aes加密完成后内容
	 * @throws @Title: aesEncryptStr
	 * @Description: aes对称加密
	 */
	public static String encode(String content, String pkey) {
		byte[] aesEncrypt = encodeForBytes(content, pkey);
		// System.out.println("加密后的byte数组:" + Arrays.toString(aesEncrypt));
		String base64EncodeStr = Base64.encodeBase64String(aesEncrypt);
		// System.out.println("加密后 base64EncodeStr:" + base64EncodeStr);
		System.out.println("input len:" + content.length() + " output len:" + base64EncodeStr.length());
		return base64EncodeStr;
	}

	/**
	 * @param content base64处理过的字符串
	 * @param pkey 密匙
	 * @return String 返回类型
	 * @throws Exception
	 * @throws @Title: aesDecodeStr
	 * @Description: 解密 失败将返回NULL
	 */
	public static String decode(String content, String pkey) {
		// try {
		// System.out.println("待解密内容:" + content);
		byte[] base64DecodeStr = Base64.decodeBase64(content);
		// System.out.println("base64DecodeStr:" + Arrays.toString(base64DecodeStr));
		byte[] aesDecode = decode(base64DecodeStr, pkey);
		// System.out.println("aesDecode:" + Arrays.toString(aesDecode));
		// if (aesDecode == null) {
		// return null;
		// }
		try {
			return new String(aesDecode, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		// System.out.println("aesDecode result:" + result);

	}

	/**
	 * 解密 128位
	 *
	 * @param content 解密前的byte数组
	 * @param pkey 密匙
	 * @return result 解密后的byte数组
	 * @throws Exception
	 */
	public static byte[] decode(byte[] content, String pkey) {
		byte[] keyBytes = pkey.getBytes();
		if (keyBytes.length != 32) {
			throw new IllegalArgumentException("key长度不是32位[" + keyBytes + "]");
		}
		// SecretKey secretKey = generateKey(pkey);
		// byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec skey = new SecretKeySpec(keyBytes, "AES");
		try {
			IvParameterSpec iv = new IvParameterSpec(IV_BYTES);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, skey, iv);// 初始化解密器
			byte[] result = cipher.doFinal(content);
			return result; // 解密
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
