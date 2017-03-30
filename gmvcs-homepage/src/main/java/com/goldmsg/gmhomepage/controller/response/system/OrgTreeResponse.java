package com.goldmsg.gmhomepage.controller.response.system;

public class OrgTreeResponse extends OrgInfoResponse {
	
	private int parentId;

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	} 

}
