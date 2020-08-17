package com.platform.au.entity;

import java.util.ArrayList;

public class FunTreeNode implements Comparable{
	private String upId;//父id
	private String appId;
	private String appCode;
	private String webProject;
	private String webHost;
	private String webPort;
	private String  inApp;
	private String funId;
	private String menuId;
	private String funCode;
	private String funcName;
	private String menuName;
	private String funOrder;
	private String funType;
	private String funUrl;
	private String smallIcon;
	private String largeIcon;
	private String showStyle;
	private ArrayList<FunTreeNode> childNodes = new ArrayList<FunTreeNode>();

	public String getUpId() {
		if("-1".equals(this.upId)){
			return "0";
		}else if(this.upId ==null){
		    return "0";
		}else if("".equals(this.upId)){
			return "0";
		}else{
			return upId;
		}
	}

	public void setUpId(String upId) {
		this.upId = upId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getFunId() {
		return funId;
	}

	public void setFunId(String funId) {
		this.funId = funId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getFunCode() {
		return funCode;
	}

	public void setFunCode(String funCode) {
		this.funCode = funCode;
	}
	
	public String getFuncName() {
		if(funcName==null || "".equals(funcName)){
			return this.menuName;
		}else{
			return funcName;
		}
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getSmallIcon() {
		if(this.smallIcon == null || "".equals(this.smallIcon)){
			return "";
		}else{
			return "/pub/uip/common/image/" + this.smallIcon;
		}
	}

	public void setSmallIcon(String smallIcon) {
		this.smallIcon = smallIcon;
	}

	public String getLargeIcon() {
		if(this.largeIcon == null || "".equals(this.largeIcon)){
			return "";
		}else{
			return "/pub/uip/common/image/" + this.largeIcon;
		}
	}

	public void setLargeIcon(String largeIcon) {
		this.largeIcon = largeIcon;
	}

	public ArrayList<FunTreeNode> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(ArrayList<FunTreeNode> childNodes) {
		this.childNodes = childNodes;
	}

	public String getWebProject() {
		return webProject;
	}

	public void setWebProject(String webProject) {
		this.webProject = webProject;
	}

	public String getWebHost() {
		return webHost;
	}

	public void setWebHost(String webHost) {
		this.webHost = webHost;
	}

	public String getWebPort() {
		return webPort;
	}

	public void setWebPort(String webPort) {
		this.webPort = webPort;
	}

	public String getShowStyle() {
		return showStyle;
	}

	public void setShowStyle(String showStyle) {
		this.showStyle = showStyle;
	}

	public String getInApp() {
		return inApp;
	}

	public void setInApp(String inApp) {
		this.inApp = inApp;
	}

	public String getFunOrder() {
		return funOrder;
	}

	public void setFunOrder(String funOrder) {
		this.funOrder = funOrder;
	}

	public String getFunType() {
		return funType;
	}

	public void setFunType(String funType) {
		this.funType = funType;
	}

	public String getFunUrl() {
		return funUrl;
	}

	public void setFunUrl(String funUrl) {
		this.funUrl = funUrl;
	}

	@Override
	public int compareTo(Object o) {
		int thisNodeOrder = (this.getFunOrder()==null||"".equals(this.getFunOrder())) ? 0 :Integer.parseInt(this.getFunOrder());
		int oNodeOrder = (((FunTreeNode)o).getFunOrder()==null||"".equals(((FunTreeNode)o).getFunOrder())) ? 0 : Integer.parseInt(((FunTreeNode)o).getFunOrder());
		
		return  thisNodeOrder - oNodeOrder;
	}
}
