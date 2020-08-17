package com.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取平台配置信息
 * @ClassName: PlatformProperties
 * @Description: 
 * @Author gzl
 */
@Component
@ConfigurationProperties(prefix = "we.config")
public class PlatformProperties {

	private String loginPageAppName;
	private boolean useConc;
	
	public String getLoginPageAppName() {
		return loginPageAppName;
	}
	public void setLoginPageAppName(String loginPageAppName) {
		this.loginPageAppName = loginPageAppName;
	}
	public boolean isUseConc() {
		return useConc;
	}
	public void setUseConc(boolean useConc) {
		this.useConc = useConc;
	}
	
}
