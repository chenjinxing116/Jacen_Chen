package com.goldmsg.gmhomepage.controller.request;

import java.util.List;

public class OrgCountRequest {

	private List<Integer> org_ids;
	
	private List<String> orgCodes;

	public List<Integer> getOrg_ids() {
		return org_ids;
	}

	public void setOrg_ids(List<Integer> org_ids) {
		this.org_ids = org_ids;
	}

	public List<String> getOrgCodes() {
		return orgCodes;
	}

	public void setOrgCodes(List<String> orgCodes) {
		this.orgCodes = orgCodes;
	}
	
}
