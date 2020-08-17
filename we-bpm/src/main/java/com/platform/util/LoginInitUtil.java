package com.platform.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.platform.au.dao.AuDao;
import com.platform.au.entity.LoginLog;
import com.platform.au.entity.Role;
import com.platform.au.service.AuService;
import com.platform.au.service.UserService;
import com.platform.session.SessionVariable;

import net.sf.json.JSONObject;
/**
 * 平台配置文件加载工具类
 *
 */
public class LoginInitUtil {
	public static HashMap<String, String> loginLog = new HashMap<String, String>(); // 平台记录的登录记录
	public static HashMap<String, String> userRoleMap = new HashMap<String, String>();// 用户当前使用角色
	
	/**
	 * 初始化Session变量
	 *
	 * @param request
	 *            Http请求
	 * @param response
	 *            Http响应
	 * @param userId
	 *            登录用户ID
	 * @param userName
	 *            登录用户名
	 * @param loginId
	 *            用户登录Id
	 * @param needWriteCookie
	 *            是否需要写入cookie
	 */
	@SuppressWarnings("unchecked")
	public static void initSession(HttpServletRequest request,
			HttpServletResponse response, String userId, String userName,
			String loginId, boolean needWriteCookie) {
		HashMap<String, String> sessionVars = null;
		HttpSession session = request.getSession();
		sessionVars = (HashMap<String, String>) session
				.getAttribute(SessionVariable.SESSION_VARS);
		if (sessionVars == null) {
			sessionVars = new HashMap<String, String>();
		}
		String tempUserId = userId;
		String tempUserName = userName;
		UserService userService = SpringUtil.getBean("userServiceImpl", UserService.class);
		if (tempUserId == null) {
			tempUserId = userService.getUserIdByName(userName);
		}
		if (tempUserName == null) {
			tempUserName = userService.getUserNameById(userId);
		}
		if (userService == null)
			userService = SpringUtil.getBean("userServiceImpl", UserService.class);
		String empName = userService.getUserEmpNameById(tempUserId);
		sessionVars.put(SessionVariable.userName, tempUserName);
		sessionVars.put(SessionVariable.userId, tempUserId);
		sessionVars.put(SessionVariable.empName, empName);
		AuService auService = SpringUtil.getBean("auServiceImpl", AuService.class);

		//String funcLogFlag = getSystemParamVal(PfConstants.FUNC_LOG_FLAG);
		boolean isResetPassword = auService.isResetPassword(tempUserId);
		boolean isIneffective = auService.isIneffectivePassword(tempUserId);
		boolean hasDefineDefaultRole = auService
				.isDefineDefaultRole(tempUserId);
		//sessionVars.put(SessionVariable.FUNC_LOG_FLAG, funcLogFlag);
		sessionVars.put(SessionVariable.isResetPassword,
				Boolean.toString(isResetPassword));
		sessionVars.put(SessionVariable.isIneffective,
				Boolean.toString(isIneffective));
		sessionVars.put(SessionVariable.hasDefineDefaultRole,
				Boolean.toString(hasDefineDefaultRole));
			String roleId = getUserRole(tempUserId);
			String defaultRoleId = auService.getDefaultRoleByUserId(tempUserId);
			Role role = null;
			if (roleId != null && !"".equals(roleId))
				role = auService.getRoleById(roleId);
			if ((defaultRoleId != null && !"".equals(defaultRoleId))
					&& role == null) {
				role = auService.getRoleById(defaultRoleId);
			}
			if (role != null) {
				sessionVars.put(SessionVariable.roleId, role.getRole_id());
				sessionVars.put(SessionVariable.roleCode, role.getRole_code());
				sessionVars.put(SessionVariable.roleName, role.getRole_name());
				setUserRole(tempUserId, role.getRole_id());
			}
		sessionVars.put(SessionVariable.recordLoginId, loginId);
		session.setAttribute(SessionVariable.SESSION_VARS, sessionVars);

		String touchPersonalSet = ConfigLoadUtil.getParamVal(
				"TOUCH_PERSONAL_SET", null, null, tempUserId);
		if (touchPersonalSet != null && !"".equals(touchPersonalSet)) {
			JSONObject ps = JSONObject.fromObject(touchPersonalSet);
			String theme = ps.getString("theme");

			if (!StringUtil.isEmpty(theme)) {
				session.setAttribute("sys.touchTheme", theme);
			}
		}
		String loginName = null;
		try {
			loginName = URLEncoder.encode(tempUserName, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (needWriteCookie) {
			//String casServerUrl = getCasServer();
			String encServerUrl = "";
			try {
				encServerUrl = URLEncoder.encode(ConfigLoadUtil.getServer(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				encServerUrl = "";
			}
			Cookie loginCookie = new Cookie("AcLogin." + encServerUrl,
					loginName);
			loginCookie.setPath("/");
			response.addCookie(loginCookie);
			Cookie loginIdCookie = new Cookie("AcLoginId." + encServerUrl,
					loginId);
			loginIdCookie.setPath("/");
			response.addCookie(loginIdCookie);
		}
	}
	
	public static void outPrint(HttpServletResponse response, String rData) {
		// 设置编码格式
		rData = rData.replaceAll("\\n", "");
		rData = rData.replaceAll("\\r", "");
		response.setContentType("text/html;charset=utf-8");
		try {
			// 输出JSON串
			response.getWriter().write(rData);
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteHost(HttpServletRequest request) {

		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

	
	/**
	 * 将用户用户登录Id和用户名写入登录记录的HashMap中
	 */
	public static void putLoginLog(String loginId, String userName) {
			loginLog.put(userName, loginId);
	}
	
	/**
	 * 登录初始化Session变量，用户名和用户ID两个必须传入一个
	 */
	public static void loginInit(HttpServletRequest request,
			HttpServletResponse response, String userId, String userName,
			String loginType, boolean needWriteLoginLog, boolean needWriteCookie) {
		String tempUserId = null;
		String tempUserName = null;
		UserService userService = SpringUtil.getBean("userServiceImpl", UserService.class);
		tempUserId = userId;
		tempUserName = userName;
		if (userId == null) {
			tempUserId = userService.getUserIdByName(userName);
		}
		if (userName == null) {
			tempUserName = userService.getUserNameById(userId);
		}
		String ip = getRemoteHost(request);
		String loginId = UUID.randomUUID().toString();
		Date loginDate = new Date();
		String terminal_name = "";
		String terminal_ip = ip;
		String os_user = "";
		String session_id = request.getSession().getId();
		LoginLog log = new LoginLog();
		log.setLogin_id(loginId);
		log.setUserId(tempUserId);
		log.setLoginDate(loginDate);
		log.setTerminal_name(terminal_name);
		log.setTerminal_ip(terminal_ip);
		log.setOs_user(os_user);
		log.setSession_id(session_id);
		log.setLogin_type(loginType);
		AuDao auDao =  SpringUtil.getBean("auDao", AuDao.class);
		try {
			auDao.insertLoginLog(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (needWriteLoginLog) {
			putLoginLog(loginId, tempUserName);
		}
		initSession(request, response, tempUserId, tempUserName, loginId,
				needWriteCookie);
	}
	
	/**
	 * 增加登录日志
	 */
	public static void addLoginLog(HttpServletRequest request,
			HttpServletResponse response, String userId, String userName,
			String loginType) {
		String tempUserId = null;
		String tempUserName = null;
		UserService userService = SpringUtil.getBean("userServiceImpl", UserService.class);
		tempUserId = userId;
		tempUserName = userName;
		if (userId == null) {
			tempUserId = userService.getUserIdByName(userName);
		}
		if (userName == null) {
			tempUserName = userService.getUserNameById(userId);
		}
		String ip = getRemoteHost(request);
		String loginId = UUID.randomUUID().toString();
		Date loginDate = new Date();
		String terminal_name = "";
		String terminal_ip = ip;
		String os_user = "";
		String session_id = request.getSession().getId();
		LoginLog log = new LoginLog();
		log.setLogin_id(loginId);
		log.setUserId(tempUserId);
		log.setLoginDate(loginDate);
		log.setTerminal_name(terminal_name);
		log.setTerminal_ip(terminal_ip);
		log.setOs_user(os_user);
		log.setSession_id(session_id);
		log.setLogin_type(loginType);
		AuDao auDao =  SpringUtil.getBean("auDao", AuDao.class);
		try {
			auDao.insertLoginLog(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 将用户的角色写入内存变量HashMap中
	 */
	public static void setUserRole(String userId, String roleId) {
		userRoleMap.put(userId, roleId);
	}

	/**
	 *根据用户ID获取该用户当前所使用的角色
	 */
	public static String getUserRole(String userId) {
		
		return userRoleMap.get(userId);
	}
}