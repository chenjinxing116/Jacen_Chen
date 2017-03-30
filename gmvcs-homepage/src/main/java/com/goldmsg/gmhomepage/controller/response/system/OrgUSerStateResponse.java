package com.goldmsg.gmhomepage.controller.response.system;

import java.util.List;

public class OrgUSerStateResponse {

	private Integer orgId;
	
	private List<UserInfoResponse> userInfo;

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public List<UserInfoResponse> getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(List<UserInfoResponse> userInfo) {
		this.userInfo = userInfo;
	}
	
	
}
