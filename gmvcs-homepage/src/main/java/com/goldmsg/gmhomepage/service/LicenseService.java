package com.goldmsg.gmhomepage.service;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldmsg.gmhomepage.helper.CryptoHelper;
import com.goldmsg.gmhomepage.helper.Tools;
import com.goldmsg.gmhomepage.jentity.TbBaseLicense;
import com.goldmsg.gmhomepage.repository.LicenseDao;
import com.goldmsg.gmhomepage.utils.SystemUtil;

/**
 * @Description:证书服务类
 * @author xiangrandy E-mail:351615708@qq.com
 * @Time:2017年3月27日下午2:12:33
 */
@Service
public class LicenseService {

	public static Logger log = LoggerFactory.getLogger(LicenseService.class);

	@Autowired
	LicenseDao licenseDao;

	private static final ThreadLocal<SimpleDateFormat> local = new ThreadLocal<SimpleDateFormat>() {

		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}

	};

	public boolean check() {
		Iterable<TbBaseLicense> l = licenseDao.findAll();
		if (l == null || !l.iterator().hasNext()) {
			return false;
		}
		TbBaseLicense license = l.iterator().next();
		String productName = license.getProductName();

		if (!productName.equals("GM-ZMS-TM V2")) {
			log.error("产品名称不正确");
			return false;
		}

		// 解析function info
		String functionInfo = license.getFunctionInfo();
		byte[] infoBytes = new byte[128];
		for (int i = 0; i < infoBytes.length; i++) {
			String curTmpStr = "F" + i + "_";
			String nextTmpStr = "F" + (i + 1) + "_";
			int beginIndex = functionInfo.indexOf(curTmpStr) + curTmpStr.length();
			int endIndex = functionInfo.indexOf(nextTmpStr);
			if (-1 == endIndex) {
				endIndex = functionInfo.length();
			}
			String valueStr = functionInfo.substring(beginIndex, endIndex);
			int value = Integer.parseInt(valueStr);
			infoBytes[i] = (byte) value;
		}

		// 按照生成license规则，拼接字符串
		String key = "GMVCS";
		String tmp = key + license.getMachineCode() + license.getSoftwareId() + productName + license.getProductInfo()
				+ license.getUserName() + license.getUserInfo() + license.getExtendInfo() + license.getCounter()
				+ license.getExpireProtect() + local.get().format(license.getExpireTime());
		byte[] info = new byte[tmp.length() + infoBytes.length];
		for (int i = 0; i < tmp.length(); i++) {
			info[i] = (byte) tmp.charAt(i);
		}
		System.arraycopy(infoBytes, 0, info, tmp.length(), infoBytes.length);
		// 计算info的MD5值,结果集为16进制字符串
		String infoMd5 = CryptoHelper.encryptToMD5(info);
		infoMd5 = CryptoHelper.encryptToMD5(Tools.hex2byte(infoMd5));
		if (!infoMd5.equalsIgnoreCase(license.getSummary())) {
			log.error("decrypt failed");
			return false;
		}

		// 对比机器码 ==> 服务器合法性
		if (!license.getMachineCode().equals(SystemUtil.getMachineCode())) {
			log.error("error machine code");
			return false;
		}
		return true;
	}

	/**
	 * @return
	 */
	public String getMachineCode() {
		return SystemUtil.getMachineCode();
	}
}
