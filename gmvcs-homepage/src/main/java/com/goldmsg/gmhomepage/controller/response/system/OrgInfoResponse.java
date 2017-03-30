package com.goldmsg.gmhomepage.controller.response.system;

import java.util.ArrayList;
import java.util.List;

public class OrgInfoResponse implements OrgUserInfoResponse {

	private int orgId;

	private String orgName;
	
	private String displayName;	//web端树插件显示所需

	private List<OrgUserInfoResponse> children = new ArrayList<OrgUserInfoResponse>();

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public List<OrgUserInfoResponse> getChildren() {
		return children;
	}

	public void setChildren(List<OrgUserInfoResponse> children) {
		this.children = children;
	}
}
