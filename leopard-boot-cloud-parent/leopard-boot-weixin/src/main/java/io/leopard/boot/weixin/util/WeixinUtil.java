package io.leopard.boot.weixin.util;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.util.StringUtils;

public class WeixinUtil {
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
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));
			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
			byte[] resultByte = cipher.doFinal(dataByte);
			if (null != resultByte && resultByte.length > 0) {
				return new String(resultByte, "UTF-8");
			}
		}
		catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidParameterSpecException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
				| BadPaddingException | UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		throw new RuntimeException("解析手机号码出错.");
	}
}
