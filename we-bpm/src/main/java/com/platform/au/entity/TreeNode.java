package com.platform.au.entity;

import java.util.ArrayList;

public class TreeNode implements Comparable{
	/**
	 * 父id
	 */
	private String upId;
	/**
	 * 应用id
	 */
	private String appId;
	/**
	 * 应用编码
	 */
	private String appCode;
	/**
	 * 工程名
	 */
	private String webProject;
	/**
	 * host
	 */
	private String webHost;
	/**
	 * port
	 */
	private String webPort;
	/**
	 * 是否项目内
	 */
	private String  inXip;
	/**
	 * 功能id
	 */
	private String funId;
	/**
	 * 菜单id
	 */
	private String menuId;
	/**
	 * 功能编码
	 */
	private String funCode;
	/**
	 * 功能名称
	 */
	private String funcName;
	/**
	 * 菜单名称
	 */
	private String menuName;
	/**
	 * 排序号
	 */
	private String nodeOrder;
	/**
	 * 菜单功能类型 M:菜单 F
	 */
	private String nodeType;
	/**
	 * 功能url
	 */
	private String url;
	/**
	 * 小图标
	 */
	private String smallIcon;
	/**
	 * 大图标
	 */
	private String largeIcon;
	/**
	 * 显示方式 页签或者窗口
	 */
	private String showStyle;
	/**
	 * 子节点
	 */
	private ArrayList<TreeNode> childNodes = new ArrayList<TreeNode>();

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

	public String getNodeOrder() {
		if(this.nodeOrder == null){
			return "9999999";
		}
		return nodeOrder;
	}

	public void setNodeOrder(String nodeOrder) {
		this.nodeOrder = nodeOrder;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getUrl() {
		if("Y".equals(this.inXip)){
			return this.url;
		}else{
			return this.webHost + this.url;
			//return this.webHost + ":" + this.webPort + "/" + this.webProject + this.url;
		}
	}

	public String getInXip() {
		return inXip;
	}

	public void setInXip(String inXip) {
		this.inXip = inXip;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSmallIcon() {
		if(this.smallIcon == null || "".equals(this.smallIcon)){
			return "";
		}else{
			return "/pub/uip/common/image/" + this.smallIcon;
			/*if("XIP".equalsIgnoreCase(this.appCode)){
				return "/pub/uip/common/image/" + this.smallIcon;
			}else{
				return this.webHost + ":" + this.webPort + "/" + this.webProject + "/pub/uip/common/image/" + this.smallIcon;
			}*/
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
			/*if("XIP".equalsIgnoreCase(this.appCode)){
				return "/pub/uip/common/image/" + this.largeIcon;
				//return "/" + this.webProject + "/pub/uip/common/image/" + this.largeIcon;
			}else{
				return this.webHost + ":" + this.webPort + "/" + this.webProject + "/pub/uip/common/icon/" + this.largeIcon;
			}*/
		}
	}

	public void setLargeIcon(String largeIcon) {
		this.largeIcon = largeIcon;
	}

	public ArrayList<TreeNode> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(ArrayList<TreeNode> childNodes) {
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

	@Override
	public int compareTo(Object o) {
		int thisNodeOrder = (this.getNodeOrder()==null||"".equals(this.getNodeOrder())) ? 0 :Integer.parseInt(this.getNodeOrder());
		int oNodeOrder = (((TreeNode)o).getNodeOrder()==null||"".equals(((TreeNode)o).getNodeOrder())) ? 0 : Integer.parseInt(((TreeNode)o).getNodeOrder());
		
		return  thisNodeOrder - oNodeOrder;
	}
}
