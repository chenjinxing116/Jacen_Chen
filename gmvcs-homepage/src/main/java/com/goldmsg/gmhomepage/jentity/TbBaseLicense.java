package com.goldmsg.gmhomepage.jentity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tb_base_license database table.
 * 
 */
@Entity
@Table(name="tb_base_license")
@NamedQuery(name="TbBaseLicense.findAll", query="SELECT t FROM TbBaseLicense t")
public class TbBaseLicense implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private int counter;

	@Column(name="expire_protect")
	private int expireProtect;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="expire_time")
	private Date expireTime;

	@Column(name="extend_info")
	private String extendInfo;

	@Lob
	@Column(name="function_info")
	private String functionInfo;

	@Column(name="license_desc")
	private String licenseDesc;

	@Column(name="machine_code")
	private String machineCode;

	@Column(name="product_info")
	private String productInfo;

	@Column(name="product_name")
	private String productName;

	@Column(name="software_id")
	private String softwareId;

	@Lob
	private String summary;

	@Column(name="user_info")
	private String userInfo;

	@Column(name="user_name")
	private String userName;

	public TbBaseLicense() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCounter() {
		return this.counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getExpireProtect() {
		return this.expireProtect;
	}

	public void setExpireProtect(int expireProtect) {
		this.expireProtect = expireProtect;
	}

	public Date getExpireTime() {
		return this.expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public String getExtendInfo() {
		return this.extendInfo;
	}

	public void setExtendInfo(String extendInfo) {
		this.extendInfo = extendInfo;
	}

	public String getFunctionInfo() {
		return this.functionInfo;
	}

	public void setFunctionInfo(String functionInfo) {
		this.functionInfo = functionInfo;
	}

	public String getLicenseDesc() {
		return this.licenseDesc;
	}

	public void setLicenseDesc(String licenseDesc) {
		this.licenseDesc = licenseDesc;
	}

	public String getMachineCode() {
		return this.machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	public String getProductInfo() {
		return this.productInfo;
	}

	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSoftwareId() {
		return this.softwareId;
	}

	public void setSoftwareId(String softwareId) {
		this.softwareId = softwareId;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getUserInfo() {
		return this.userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}