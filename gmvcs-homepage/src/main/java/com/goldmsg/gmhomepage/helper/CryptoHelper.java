package com.goldmsg.gmhomepage.helper;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class CryptoHelper {
	/**
	 * 计算MD5值
	 * 
	 * @param info
	 * @return
	 */
	public static String encryptToMD5(byte[] info) {
		byte[] md5Digest = null;
		try {
			MessageDigest md5Algorithm = MessageDigest.getInstance("MD5");
			md5Algorithm.update(info);
			md5Digest = md5Algorithm.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
		return Tools.byte2hex(md5Digest);
	}

	/**
	 * 计算SHA-1值
	 * 
	 * @param info
	 * @return
	 */
	public static String encryptToSHA(byte[] info) {
		byte[] shaDigest = null;
		try {
			MessageDigest shaAlgorithm = MessageDigest.getInstance("SHA-1");
			shaAlgorithm.update(info);
			shaDigest = shaAlgorithm.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
		return Tools.byte2hex(shaDigest);
	}

	/**
	 * AES加密，密钥为十六进制字符串
	 * 
	 * @param info
	 * @return
	 */
	public static String encryptToAES(byte[] info, String keyHex) {
		final String ALGORITHM = "AES";
		byte[] key = Tools.hex2byte(keyHex);
		SecretKey aesKey = new SecretKeySpec(key, ALGORITHM);

		byte[] cipherByte = null;
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			cipherByte = cipher.doFinal(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Tools.byte2hex(cipherByte);
	}

	/**
	 * AES加密，密钥,info均为十六进制字符串
	 * 
	 * @param info
	 * @return
	 */
	public static byte[] decryptByAES(String info, String keyHex) {
		final String ALGORITHM = "AES";
		byte[] key = Tools.hex2byte(keyHex);
		SecretKey aesKey = new SecretKeySpec(key, ALGORITHM);

		byte[] infoByte = Tools.hex2byte(info);
		byte[] cipherByte = null;
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			cipherByte = cipher.doFinal(infoByte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cipherByte;
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		/**
		 * java.security.Provider: 提供者类
		 *
		 * java.security.Security: 管理Provider
		 *
		 * java.security.Key: 所有密钥的顶层接口，必须可以序列化。
		 *
		 * javax.crypto.SecretKey: 对称密钥接口，实现了Key接口。
		 *
		 * java.security.PublicKey,java.security.PrivateKey: 非对称密钥接口，实现了Key接口
		 *
		 * java.security.AlgorithmParameters: 提供密码参数的不透明表示，不透明表示即：不可直接访问各参数域
		 *
		 * java.security.KeyPair: 对非对称密钥的扩展, This class is a simple holder for a
		 * key pair (a public key and a private key).
		 *
		 * java.security.KeyPairGenerator: 密钥对生成器, KeyPairGenerator keygen =
		 * KeyPairGenerator.getInstance("DSA");可在getInstance指定Provider
		 *
		 * java.security.spec.KeySpec: 接口定义了密钥规范,,PKCS8EncodedKeySpec等实现了它
		 *
		 * java.security.KeyFactory: 用来生成密钥对象，通过密钥规范还原密钥,
		 *
		 * java.security.SecureRandom: 继承自java.util.Random
		 *
		 * java.security.Signature:用来生成和验证数字签名
		 *
		 * java.security.KeyStore: 密钥库，用于管理密钥和证书的存储
		 */

		/**
		 * javax.crypto.KeyGenerator: 与KeyPairGenerator类似，产生秘密密钥
		 *
		 * javax.crypto.SecretKeyFactory: 与KeyFactory类似
		 *
		 * javax.crypto.Cipher:
		 */

		/**
		 * java.security.spec.KeySpec: 本身是空接口，用于将所有参数规范分组
		 *
		 * X509EncodedKeySpec: 用于公钥规范
		 *
		 * PKCS8EncodedKeySpec：用于私钥规范
		 *
		 * SecretKeySpec：用于秘密密钥规范
		 */

		return;
	}
}