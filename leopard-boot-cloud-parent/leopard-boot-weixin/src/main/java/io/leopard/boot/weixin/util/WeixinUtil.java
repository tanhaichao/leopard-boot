package io.leopard.boot.weixin.util;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.util.StringUtils;

import io.leopard.json.Json;

public class WeixinUtil {
	protected static Log logger = LogFactory.getLog(WeixinUtil.class);

	public static String getUserInfo(String encryptedData, String sessionKey, String iv) {

		// // 被加密的数据
		//
		// byte[] dataByte = Base64.decode(encryptedData);
		//
		// // 加密秘钥
		//
		// byte[] keyByte = Base64.decode(sessionKey);
		//
		// // 偏移量
		//
		// byte[] ivByte = Base64.decode(iv);

		byte[] dataByte = Base64.decodeBase64(encryptedData);
		byte[] keyByte = Base64.decodeBase64(sessionKey);
		byte[] ivByte = Base64.decodeBase64(iv);

		// System.err.println("keyByte:" + keyByte.length);
		try {

			// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要

			int base = 16;

			if (keyByte.length % base != 0) {

				int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);

				byte[] temp = new byte[groups * base];

				Arrays.fill(temp, (byte) 0);

				System.arraycopy(keyByte, 0, temp, 0, keyByte.length);

				keyByte = temp;

			}

			// 初始化

			Security.addProvider(new BouncyCastleProvider());

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");

			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");

			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");

			parameters.init(new IvParameterSpec(ivByte));

			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化

			byte[] resultByte = cipher.doFinal(dataByte);

			if (null != resultByte && resultByte.length > 0) {

				String result = new String(resultByte, "UTF-8");
				logger.info("result:" + result);
				return result;

			}

		}
		catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	/**
	 * 解析手机号
	 * 
	 * @param sessionKey
	 * @param encryptedData
	 * @param iv
	 * @return
	 * @throws Exception
	 */
	public static String getMobile(String sessionKey, String encryptedData, String iv) {
		if (StringUtils.isEmpty(sessionKey)) {
			throw new IllegalArgumentException("sessionKey不能为空");
		}
		if (StringUtils.isEmpty(encryptedData)) {
			throw new IllegalArgumentException("encryptedData不能为空");
		}
		if (StringUtils.isEmpty(iv)) {
			throw new IllegalArgumentException("iv不能为空");
		}

		byte[] dataByte = Base64.decodeBase64(encryptedData);
		byte[] keyByte = Base64.decodeBase64(sessionKey);
		byte[] ivByte = Base64.decodeBase64(iv);

		int base = 16;
		if (keyByte.length % base != 0) {
			int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
			byte[] temp = new byte[groups * base];
			Arrays.fill(temp, (byte) 0);
			System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
			keyByte = temp;
		}
		// 初始化
		Security.addProvider(new BouncyCastleProvider());
		SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");

		try {
			// Security.addProvider(new BouncyCastleProvider());

			// Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			// Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));
			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
			byte[] resultByte = cipher.doFinal(dataByte);
			if (null != resultByte && resultByte.length > 0) {
				String json = new String(resultByte, "UTF-8");
				Map<String, Object> map = Json.toMap(json);
				logger.info("json:" + json);
				logger.info("map:" + map);
				return (String) map.get("phoneNumber");
			}
		}
		catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidParameterSpecException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
				| UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		catch (BadPaddingException e) {
			throw new RuntimeException("sessionKey已失效!");
		}
		throw new RuntimeException("解析手机号码出错.");
	}
}
