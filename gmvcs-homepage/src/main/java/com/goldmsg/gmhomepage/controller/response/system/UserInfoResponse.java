package com.goldmsg.gmhomepage.controller.response.system;

public class UserInfoResponse extends BaseUserInfoResponse {


	// 执法仪类型
	private Integer dsjType;

	// 是否在线
	private boolean isOnline = false;

	public Integer getDsjType() {
		return dsjType;
	}

	public void setDsjType(Integer dsjType) {
		this.dsjType = dsjType;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

}
