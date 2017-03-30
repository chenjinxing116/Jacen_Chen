package com.goldmsg.gmhomepage.controller.response.system;

import java.util.ArrayList;
import java.util.List;

/**
 * 当前部门的在线人数
 */
public class OrgOnlineResponse {

	//当前部门在线人数
	private int orgCount;
	
	//当前部门的总人数
	private int totalCount;

	private int orgId;

	private String orgName;
	
	private String displayName;	//web端树插件显示所需

	private List<OrgOnlineResponse> children = new ArrayList<OrgOnlineResponse>();

	public int getOrgCount() {
		return orgCount;
	}

	public void setOrgCount(int orgCount) {
		this.orgCount = orgCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public List<OrgOnlineResponse> getChildren() {
		return children;
	}

	public void setChildren(List<OrgOnlineResponse> children) {
		this.children = children;
	}
	
}
