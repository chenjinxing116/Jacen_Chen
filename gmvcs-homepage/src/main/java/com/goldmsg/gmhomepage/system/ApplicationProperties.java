package com.goldmsg.gmhomepage.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/***
 * application.properties中的配置参数映射类
 * @author QH
 *
 */
@Component
public class ApplicationProperties {

	/*
	 * 安全认证地址
	 */
	@Value("#{globalReader['security.url']}")
	private String securityUrl;

	public String getSecurityUrl() {
		return securityUrl;
	}

	public void setSecurityUrl(String securityUrl) {
		this.securityUrl = securityUrl;
	}
}
