package com.platform.common.modal;

/**
 * 组织隔离参数
 */
public class OrgSec {
	private String userId;//用户id
	private String roleId;//职责id
	private String funId;//功能id
	private String appId;//应用id
	private boolean userIdFlag = true;
	private boolean roleIdFlag = true;
	private boolean funIdFlag = true;
	private boolean appIdFlag = true;
	private boolean selfFlag = true;//当前登陆人关联员工所在的组织
	
	public OrgSec(String userId, String roleId, String funId, String appId,
			boolean userIdFlag, boolean roleIdFlag, boolean funIdFlag,
			boolean appIdFlag) {
		super();
		this.userId = userId;
		this.roleId = roleId;
		this.funId = funId;
		this.appId = appId;
		this.userIdFlag = userIdFlag;
		this.roleIdFlag = roleIdFlag;
		this.funIdFlag = funIdFlag;
		this.appIdFlag = appIdFlag;
	}
	
	public OrgSec(String userId, String roleId, String funId, String appId) {
		super();
		this.userId = userId;
		this.roleId = roleId;
		this.funId = funId;
		this.appId = appId;
	}
	//只传功能id和应用id
	public OrgSec(String funId, String appId) {
		super();
		this.funId = funId;
		this.appId = appId;
	}
	public OrgSec() {
		super();
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getFunId() {
		return funId;
	}
	public void setFunId(String funId) {
		this.funId = funId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public boolean getUserIdFlag() {
		return userIdFlag;
	}
	public void setUserIdFlag(boolean userIdFlag) {
		this.userIdFlag = userIdFlag;
	}
	public boolean getRoleIdFlag() {
		return roleIdFlag;
	}
	public void setRoleIdFlag(boolean roleIdFlag) {
		this.roleIdFlag = roleIdFlag;
	}
	public boolean getFunIdFlag() {
		return funIdFlag;
	}
	public void setFunIdFlag(boolean funIdFlag) {
		this.funIdFlag = funIdFlag;
	}
	public boolean getAppIdFlag() {
		return appIdFlag;
	}
	public void setAppIdFlag(boolean appIdFlag) {
		this.appIdFlag = appIdFlag;
	}

	public boolean getSelfFlag() {
		return selfFlag;
	}

	public void setSelfFlag(boolean selfFlag) {
		this.selfFlag = selfFlag;
	}

}
