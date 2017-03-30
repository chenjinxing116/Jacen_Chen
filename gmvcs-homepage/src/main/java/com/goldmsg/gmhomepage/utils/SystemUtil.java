package com.goldmsg.gmhomepage.utils;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.goldmsg.gmhomepage.helper.CryptoHelper;

/**
 * @Description:获取操作系统信息工具类
 * @author xiangrandy E-mail:351615708@qq.com
 * @Time:2017年3月27日下午2:18:48
 */
public class SystemUtil {

	/**
	 * get operation system version string
	 * 
	 * @return
	 */
	public static String getOSVersion() {
		return System.getProperty("os.name");
	}

	/**
	 * get operation system current login user
	 * 
	 * @return
	 */
	public static String getLoginUser() {
		return System.getProperty("user.name");
	}

	/**
	 * get operation system language
	 * 
	 * @return
	 */
	public static String getOSLanguage() {
		return System.getProperty("user.language");
	}

	/**
	 * get operation system computer name
	 * 
	 * @return
	 */
	public static String getComputerName() {
		String hostname = "";

		try {
			hostname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return hostname;
	}

	public static String getMachineCode() {
		String mac = SystemUtil.getMacAddress();
		String md5 = CryptoHelper.encryptToMD5(mac.getBytes());
		md5 = CryptoHelper.encryptToMD5(md5.getBytes());
		String machineCode = "";
		for (int i = 0; i < md5.length(); i = i + 2) {
			machineCode += md5.charAt(i);
		}
		return machineCode.substring(0, 15).toUpperCase();
	}

	/**
	 * get all the host ip address
	 * 
	 * @return
	 */
	public static List<String> getIpAddress() {

		List<String> ipList = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();

			while (netInterfaces.hasMoreElements()) {
				Enumeration<InetAddress> ipEnum = netInterfaces.nextElement().getInetAddresses();

				while (ipEnum.hasMoreElements()) {
					InetAddress ip = (InetAddress) ipEnum.nextElement();

					if (ip.getHostAddress().contains(":"))
						continue;

					if (ip.getHostAddress().contains("127.0.0.1"))
						continue;

					ipList.add(ip.getHostAddress());
				}
			}

		} catch (SocketException e) {
			e.printStackTrace();
		}
		return ipList;
	}

	/**
	 * get the host mac address
	 * 
	 * @return
	 */
	public static String getMacAddress() {
		Pattern regexPattern = Pattern.compile(".*\\. : ((?:\\w{2}-){5}\\w{2})");
		String address = "";
		String os = getOSVersion();
		try {
			if (os.startsWith("Windows")) {
				String command = "cmd.exe /c ipconfig /all";
				Process p = Runtime.getRuntime().exec(command);
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while ((line = br.readLine()) != null) {
					Matcher m = regexPattern.matcher(line);
					if (m.matches()) {
						address = m.group(1);
					}
				}
				br.close();
			} else if (os.startsWith("Linux")) {
				String command = "/bin/sh -c ifconfig -a";
				Process p = Runtime.getRuntime().exec(command);
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while ((line = br.readLine()) != null) {
					if (line.indexOf("HWaddr") > 0) {
						int index = line.indexOf("HWaddr") + "HWaddr".length();
						address = line.substring(index);
						break;
					}
				}
				br.close();
			}
		} catch (IOException ignored) {
		}
		return address.trim();
	}

}
