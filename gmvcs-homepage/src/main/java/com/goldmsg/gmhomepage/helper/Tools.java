package com.goldmsg.gmhomepage.helper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Tools {

	/**
	 * 二进制转十六进制字符串
	 * 
	 * @param bytes
	 * @return
	 */
	public static String byte2hex(byte[] bytes) {
		String retString = new String();

		for (int i = 0; i < bytes.length; i++) {
			String hexStr = Integer.toHexString(bytes[i] & 0xff);
			if (1 == hexStr.length()) {
				retString = retString + "0" + hexStr;
			} else {
				retString = retString + hexStr;
			}
		}
		return retString;
	}

	/**
	 * 十六进制字符串转二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] hex2byte(String hexStr) {
		if (hexStr.length() % 2 != 0) {
			return null;
		}

		hexStr = hexStr.toLowerCase();
		byte[] retBytes = new byte[hexStr.length() / 2];
		for (int i = 0; i < retBytes.length; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);

			int value = (high << 4) & 0xf0;
			value = value | (low & 0x0f);
			retBytes[i] = (byte) value;
		}
		return retBytes;
	}

	/**
	 * 序列化保存对象
	 * 
	 * @param file
	 * @param obj
	 */
	public static void saveObjectToFile(String file, Object obj) {
		ObjectOutputStream objectOutputStream = null;
		try {
			objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
			objectOutputStream.writeObject(obj);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				objectOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读取文件反序列化对象
	 * 
	 * @param file
	 * @return
	 */
	public static Object getObjectFromFile(String file) {
		ObjectInputStream objectInputStream = null;
		Object retObject = null;
		try {
			objectInputStream = new ObjectInputStream(new FileInputStream(file));
			retObject = objectInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				objectInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return retObject;
	}
}