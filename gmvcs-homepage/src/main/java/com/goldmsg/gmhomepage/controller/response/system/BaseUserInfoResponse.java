package com.goldmsg.gmhomepage.controller.response.system;

/***
 * 基础用户信息response
 * 
 * @author QH
 * @Email: qhs_dream@163.com
 * @Date: 2017年1月12日: 下午8:10:33
 */
public class BaseUserInfoResponse implements OrgUserInfoResponse {

	// 用户主键ID
	private int accountId;

	// 用户名
	private String accountName;

	// 警号
	private String userCode;

	// 姓名
	private String userName;

	// web端树插件显示所需字段
	private String displayName;
	
	// 部门id
	private Integer orgId;

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
}
