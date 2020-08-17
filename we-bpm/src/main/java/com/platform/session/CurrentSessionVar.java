package com.platform.session;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

/**
 * @ClassName: CurrentSessionVar 
 *
 * @Description: 获取Session变量值
 */
public class CurrentSessionVar {
	
	/**
	 * @Title: getSession 
	 * @Description: 取得Session对象
	 * @return
	 * @return HttpSession    返回类型
	 */
	public static HttpSession getSession(){
	    return CustomSessionContext.getCurrentSession();
	}
	
	/**
	 * @Title: getUserId 
	 * @Description: 取得用户ID
	 * @param session
	 * @return
	 * @return String    返回类型
	 */
	@SuppressWarnings("unchecked")
	public static String getUserId(HttpSession session) {
		
		HashMap<String, Object> hashmap = (HashMap<String, Object>) session
				.getAttribute(SessionVariable.SESSION_VARS);
		
		return (String) hashmap.get(SessionVariable.userId);
	}

	/**
	 * @Title: getUserId 
	 * @Description: 取得用户ID
	 * @return
	 * @return String    返回类型
	 */
	public static String getUserId(){
		HttpSession session = getSession();
		return getUserId(session) ;
	}
	
	/**
	 * @Title: getUserName 
	 * @Description: 取得用户名称
	 * @return
	 * @return String    返回类型
	 */
	@SuppressWarnings("unchecked")
	public static String getUserName(){
		HttpSession session = getSession();
		HashMap<String, Object> hashmap = (HashMap<String, Object>) session
				.getAttribute(SessionVariable.SESSION_VARS);
		return (String) hashmap.get(SessionVariable.userName);	
	}
	
	/**
	 * @Title: getEmpName 
	 * @Description: 取得员工姓名
	 * @return
	 * @return String    返回类型
	 */
	@SuppressWarnings("unchecked")
	public static String getEmpName(){
		HttpSession session = getSession();
		HashMap<String, Object> hashmap = (HashMap<String, Object>) session
				.getAttribute(SessionVariable.SESSION_VARS);
		return (String) hashmap.get(SessionVariable.empName);	
	}
	
	/**
	 * @Title: getRoleId 
	 * @Description: 取得角色ID
	 * @param session
	 * @return
	 * @return String    返回类型
	 */
	@SuppressWarnings("unchecked")
	public static String getRoleId(HttpSession session){
		
		HashMap<String, Object> hashmap = (HashMap<String, Object>) session
				.getAttribute(SessionVariable.SESSION_VARS);
		
		return (String) hashmap.get(SessionVariable.roleId);		
	}
	
	/**
	 * @Title: getRoleId 
	 * @Description: 取得角色ID
	 * @return
	 * @return String    返回类型
	 */
	public static String getRoleId(){
		HttpSession session = getSession();
		return getRoleId(session) ;
	}
	
	/**
	 * @Title: getRoleCode 
	 * @Description: 取得角色CODE
	 * @param session
	 * @return String    返回类型
	 */
	@SuppressWarnings("unchecked")
	public static String getRoleCode(HttpSession session){
		HashMap<String, Object> hashmap = (HashMap<String, Object>) session
				.getAttribute(SessionVariable.SESSION_VARS);
		
		return (String) hashmap.get(SessionVariable.roleCode);	
	}
	
	/**
	 * @Title: getRoleCode 
	 * @Description: 取得角色CODE
	 * @return String    返回类型
	 */
	public static String getRoleCode(){
		HttpSession session = getSession();
		return getRoleCode(session) ;
	}
}
