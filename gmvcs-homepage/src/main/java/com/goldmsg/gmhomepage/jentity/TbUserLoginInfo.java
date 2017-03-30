package com.goldmsg.gmhomepage.jentity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the tb_user_login_info database table.
 * 
 */
@Entity
@Table(name = "tb_user_login_info")
@NamedQuery(name = "TbUserLoginInfo.findAll", query = "SELECT t FROM TbUserLoginInfo t")
public class TbUserLoginInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "account_id")
	private int accountId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login_time")
	private java.util.Date lastLoginTime;

	@Column(name = "user_code")
	private String userCode;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "ip")
	private String ip;

	public TbUserLoginInfo() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public java.util.Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}