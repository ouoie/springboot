package com.greathealth.greathealth.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;

public class HealthSm4Util {

	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	private static final String ENCODING = "UTF-8";
	public static final String ALGORITHM_NAME = "SM4";
	// 定义分组加密模式使用：PKCS5Padding
	public static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS5Padding";

	public static final String HEX_KEY = "09582c69c42a4de9bab785d51be7b5e3";

	/**
	 * 生成ECB暗号
	 *
	 * @param algorithmName
	 *            算法名称
	 * @param mode
	 *            模式
	 * @param key
	 * @return
	 * @throws Exception
	 * @explain ECB模式（电子密码本模式：Electronic codebook）
	 */
	private static Cipher generateEcbCipher(String algorithmName, int mode,
			byte[] key) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithmName,
				BouncyCastleProvider.PROVIDER_NAME);
		Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
		cipher.init(mode, sm4Key);
		return cipher;
	}

	/**
	 * sm4加密
	 *
	 * @param hexKey
	 *            16进制密钥（忽略大小写）
	 * @param paramStr
	 *            待加密字符串
	 * @return 返回16进制的加密字符串
	 * @explain 加密模式：ECB 密文长度不固定，会随着被加密字符串长度的变化而变化
	 */
	public static String encryptEcb(String paramStr) {
		try {
			String cipherText = "";
			// 16进制字符串--&gt;byte[]
			byte[] keyData = ByteUtils.fromHexString(HEX_KEY);
			// String--&gt;byte[]
			byte[] srcData = paramStr.getBytes(ENCODING);
			// 加密后的数组
			byte[] cipherArray = encrypt_Ecb_Padding(keyData, srcData);
			// byte[]--&gt;hexString
			cipherText = ByteUtils.toHexString(cipherArray);
			return cipherText;
		} catch (Exception e) {
			return paramStr;
		}
	}

	/**
	 * 加密模式之Ecb
	 *
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception
	 * @explain
	 */
	public static byte[] encrypt_Ecb_Padding(byte[] key, byte[] data)
			throws Exception {
		Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING,
				Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(data);
	}

	/**
	 * sm4解密
	 *
	 * @param hexKey
	 *            16进制密钥
	 * @param cipherText
	 *            16进制的加密字符串（忽略大小写）
	 * @return 解密后的字符串
	 * @throws Exception
	 * @explain 解密模式：采用ECB
	 */
	public static String decryptEcb(String cipherText) {
		// 用于接收解密后的字符串
		String decryptStr = "";
		// hexString--&gt;byte[]
		byte[] keyData = ByteUtils.fromHexString(HEX_KEY);
		// hexString--&gt;byte[]
		byte[] cipherData = ByteUtils.fromHexString(cipherText);
		// 解密
		byte[] srcData = new byte[0];
		try {
			srcData = decrypt_Ecb_Padding(keyData, cipherData);
			// byte[]--&gt;String
			decryptStr = new String(srcData, ENCODING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptStr;
	}

	/**
	 * 解密
	 *
	 * @param key
	 * @param cipherText
	 * @return
	 * @throws Exception
	 * @explain
	 */
	public static byte[] decrypt_Ecb_Padding(byte[] key, byte[] cipherText)
			throws Exception {
		Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING,
				Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(cipherText);
	}
}
