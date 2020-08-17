package com.platform.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.config.PlatformProperties;
/**
 * 平台配置文件加载工具类
 *
 */
public class ConfigLoadUtil {
	//private static Properties config = null; // 平台配置文件
	private static Map<String, Object> config = null; // 平台版本信息
	private static String dbType = null; // 平台数据库类型
    public static String useRedis = "false";// 是否启用Redis缓存内存变量内容
	private static HashMap<String, Object> verMap = null; // 平台版本信息
	public static HashMap<String, String> loginLog = new HashMap<String, String>(); // 平台记录的登录记录
    public static ArrayList<String> filterExclusion = new ArrayList<String>(); // 登录验证过滤器需要忽略的URL列表
	private static HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>> acParams = new HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>>(); // 平台所有参数以及值都将缓存在此变量中
	//public static HashMap<String, String> attServTypes = new HashMap<String, String>(); // 平台定义的附件服务器类型
	public static ArrayList<String> onlyAcUserUrl = new ArrayList<String>(); // 仅供平台使用URL列表
	
	/**
	 * 加载平台配置文件
	 * @throws Exception
	 */
	public static void loadAcConfig(PlatformProperties platformProperties) {
		config = JSONUtil.beanToMap(platformProperties, null);
	}

	
	
	
	/**
	 * 初始化过滤器忽略URL列表
	 */
	public static void initFilterExclusion() {
		filterExclusion.clear();
		filterExclusion.add("/login/getVcode");//待修改
		filterExclusion.add("/login/personalLogin");//待修改
	}

	
	/**
	 * getAcConfigVal:(获取平台配置文件中的配置值)
	 *
	 * @param keyName
	 *            配置键值
	 * @return 配置键值对应的配置值
	 */
	public static String getAcConfigVal(String keyName) {
		if (config != null) {
			return String.valueOf(config.get(keyName));
		} else {
			return "";
		}
	} 
	
	/**
	 *加载过滤器要排除的页面的列表....
	 */
	public static void loadFilterExclusion(Connection conn) {
		String loginUrl = ConfigLoadUtil.getSystemParamVal("LOGIN_PAGE_URL");
		if (loginUrl != null && !"".equals(loginUrl.trim())) {
			ConfigLoadUtil.addFilterExclusion(loginUrl);
		}
		String loginCheckUrl = ConfigLoadUtil
				.getSystemParamVal("LOGIN_CHECK_URL");
		if (loginCheckUrl != null && !"".equals(loginCheckUrl.trim())) {
			ConfigLoadUtil.addFilterExclusion(loginCheckUrl);
		}
		loadOnlyAcUseUrl(conn);
	}
	/**
	 * 获取系统参数值
	 */
	public static String getSystemParamVal(String paramCode) {
		HashMap<String, String> valList = getParamValList(paramCode, "P");
		if ("NAVIGATOR_MODEL".equals(paramCode)) {
			//do..
		}
		if (valList == null) {
			return "";
		}
		return valList.get("P");
	}
	
	/**
	 * 获取参数值
	 */
	public static String getParamVal(String paramCode, String valLevel,
			String targetId) {
		HashMap<String, String> valList = getParamValList(paramCode, valLevel);
		if (valList == null) {
			return null;
		} else {
			return valList.get(targetId);
		}
	}
	/**
	 * getParamVal:(从内存中获取参数值（此数据不是实时刷新），获取参数值的次序为 用户级 -》角色级 -》 应用 级 -》 平台级)
	 *
	 * @param paramCode
	 *            参数编码
	 * @param appId
	 *            应用ID
	 * @param roleId
	 *            角色ID
	 * @param userId
	 *            用户ID
	 * @return 返回参数值，如果没有值则返回空串
	 */
	public static String getParamVal(String paramCode, String appId,
			String roleId, String userId) {
		String val = null;
		if (userId != null && !"".equals(userId.trim())) {
			val = getParamVal(paramCode, "U", userId);
			if (val != null && !"".equals(val.trim())) {
				return val;
			}
		}
		if (roleId != null && !"".equals(roleId.trim())) {
			val = getParamVal(paramCode, "R", roleId);
			if (val != null && !"".equals(val.trim())) {
				return val;
			}
		}
		if (appId != null && !"".equals(appId.trim())) {
			val = getParamVal(paramCode, "R", appId);
			if (val != null && !"".equals(val.trim())) {
				return val;
			}
		}
		val = getParamVal(paramCode, "P", "P");
		if (val != null && !"".equals(val.trim())) {
			return val;
		} else {
			return "";
		}
	}

	public static HashMap<String, String> getParamValList(String paramCode,
			String valLevel) {
		HashMap<String, HashMap<String, HashMap<String, String>>> params = null;
		params = acParams.get("we");
		if (params == null) {
			return null;
		} else {
			HashMap<String, HashMap<String, String>> param = params
					.get(paramCode);
			if (param == null) {
				return null;
			}
			HashMap<String, String> valList = param.get(valLevel);
			return valList;
		}
	}

	/**
	 * 加载仅供平台使用页面的列表
	 */
	public static void loadOnlyAcUseUrl(Connection conn) {
		//do..
	}
	
	/**
	 * 根据配置从内存中获取过滤器排除列表，此列表内的URL不进行过滤
	 *
	 * @return 过滤器排除列表
	 */
	public static ArrayList<String> getFilterExclusion() {
			return filterExclusion;
	}
	
	/**
	 * 根据配置从内存中获取仅供平台使用页面列表
	 *
	 * @return 仅供平台使用页面列表
	 */
	public static ArrayList<String> getOnlyAcUseUrl() {
			return onlyAcUserUrl;
	}
	public static String getServer() {
		return getSystemParamVal("SERVER_URL");
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
	 * 想过滤器排除列表中添加URL
	 */
	public static void addFilterExclusion(String url) {
		filterExclusion.add(url);
	}
	
	
	/**
	 * getDbType:获取数据库类型
	 */
	public static String getDbType() {
		if (dbType != null) {
			return dbType;
		} else {
			try {
				dbType = "oracle";
				return dbType;
			} catch (Exception e) {
				return "oracle";
			}
		}
	}
	public static void setDbType(String dbTypeString) {
		dbType = dbTypeString;
	}

}