package com.goldmsg.gmhomepage.controller.response.system;

import java.util.ArrayList;
import java.util.List;

/***
 * 用户状态response
 * 
 * @author QH
 * @Email: qhs_dream@163.com
 * @Date: 2017年1月12日: 下午1:54:29
 */
public class UserStatusResponse {

	private Integer accountId; // 用户id
	private Boolean online = false; // 是否在线

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

}
