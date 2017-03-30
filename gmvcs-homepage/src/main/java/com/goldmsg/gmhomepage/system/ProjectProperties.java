package com.goldmsg.gmhomepage.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProjectProperties {
	
	/*
	 * 项目代号
	 */
	@Value("#{localPropertiesReader['project.code']}")
	private String projectCode;
	
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
}
