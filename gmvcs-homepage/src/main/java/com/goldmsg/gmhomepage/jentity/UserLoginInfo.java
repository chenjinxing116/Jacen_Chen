package com.goldmsg.gmhomepage.jentity;

import java.util.Date;

/**
 * @Description:用户登录信息响应实体
 * @author xiangrandy E-mail:351615708@qq.com
 * @Time:2017年2月16日下午4:37:30
 */
public class UserLoginInfo {

	private String userCode;

	private Date lastLoginTime;

	public UserLoginInfo() {
		super();
	}

	public UserLoginInfo(String userCode, Date lastLoginTime) {
		super();
		this.userCode = userCode;
		this.lastLoginTime = lastLoginTime;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

}
