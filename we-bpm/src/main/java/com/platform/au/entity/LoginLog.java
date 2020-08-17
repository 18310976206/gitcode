package com.platform.au.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginLog implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String login_id;
	private String userId;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date   loginDate;
	private Date   logoutDate;
	private String terminal_name;
	private String terminal_ip;
	private String os_user;
	private String session_id;
	private String login_type;
	
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",locale = "zh" , timezone="GMT+8")
	public Date getLoginDate() {
		return loginDate;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",locale = "zh" , timezone="GMT+8")
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	public Date getLogoutDate() {
		return logoutDate;
	}
	public void setLogoutDate(Date logoutDate) {
		this.logoutDate = logoutDate;
	}
	public String getTerminal_name() {
		return terminal_name;
	}
	public void setTerminal_name(String terminal_name) {
		this.terminal_name = terminal_name;
	}
	public String getTerminal_ip() {
		return terminal_ip;
	}
	public void setTerminal_ip(String terminal_ip) {
		this.terminal_ip = terminal_ip;
	}
	public String getOs_user() {
		return os_user;
	}
	public void setOs_user(String os_user) {
		this.os_user = os_user;
	}
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public String getLogin_type() {
		return login_type;
	}
	public void setLogin_type(String login_type) {
		this.login_type = login_type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
