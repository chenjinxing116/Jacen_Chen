package com.goldmsg.gmhomepage.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldmsg.gmhomepage.jentity.TbUserLoginInfo;
import com.goldmsg.gmhomepage.jentity.UserLoginInfo;
import com.goldmsg.gmhomepage.repository.LoginDao;

/**
 * @Description:登录信息服务
 * @author xiangrandy E-mail:351615708@qq.com
 * @Time:2017年2月16日下午3:17:47
 */
@Service
public class LoginService {

	@Autowired
	LoginDao loginDao;

	public void saveLoginInfo(int accountId, String userCode, String userName, String ip) {
		TbUserLoginInfo loginInfo = new TbUserLoginInfo();
		loginInfo.setAccountId(accountId);
		loginInfo.setLastLoginTime(new Date());
		loginInfo.setIp(ip);
		loginInfo.setUserCode(userCode);
		loginInfo.setUserName(userName);
		loginDao.save(loginInfo);
	}

	/**
	 * @param accountIds
	 * @return
	 */
	public Map<String, Date> getLastLoginInfosByUserCodes(List<String> userCodes) {
		List<UserLoginInfo> l = loginDao.getLastLoginInfosByUserCodes(userCodes);
		if (l == null || l.isEmpty()) {
			return new HashMap<>(0);
		}
		Map<String, Date> map = new HashMap<>();
		for (UserLoginInfo r : l) {
			map.put(r.getUserCode(), r.getLastLoginTime());
		}
		return map;
	}
}
