package com.platform.au.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.au.entity.FunTreeNode;
import com.platform.au.entity.User;
import com.platform.au.service.AuService;
import com.platform.au.service.UserService;
import com.platform.session.SessionVariable;
import com.platform.util.ConfigLoadUtil;
import com.platform.util.ImageUtil;
import com.platform.util.LoginInitUtil;

import net.sf.json.JSONObject;

/**
 * 平台用户登录
 **/
@Controller
@RequestMapping("/we/login")
public class LoginController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private AuService auService;
	

	@Autowired
	private UserService userService;

	/**
	 * 加载导航树
	 **/
	@SuppressWarnings("rawtypes")
	@RequestMapping("/loadNavigatTree")
	public void loadNavigatTree(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, String> sessionVars = (HashMap<String, String>) request.getSession().getAttribute(SessionVariable.SESSION_VARS);
		// Parameter parameter=ParameterParser.parseParams(request);
		String json = null;String jsondata = "";
		HashMap map = new HashMap();
		map.put("roleId", sessionVars.get("roleId"));
		FunTreeNode funTreeNode = auService.loadMnuForRole(map);
		// 解析菜单导航树
		json = parseFunTree(funTreeNode, "0", request);
		//输出菜单树
		try {
			  jsondata = mnuTransDataJson(json,sessionVars);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(jsondata);
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String parseFunTree(FunTreeNode treeNode, String rootId,HttpServletRequest request){
		HashMap<String, String> sessionVars = (HashMap<String, String>) request.getSession().getAttribute(SessionVariable.SESSION_VARS);
		String userId = sessionVars.get(SessionVariable.userId);
		String roleId =LoginInitUtil.getUserRole(userId);
		String userName = sessionVars.get(SessionVariable.userName);
		String roleCode = sessionVars.get(SessionVariable.roleCode);
		
		HashMap<String, String> par = new HashMap<String, String>();
		par.put("userId", userId);
		par.put("roleId", roleId);
		par.put("userName", userName);
		par.put("roleCode", roleCode);
		
		String menuTreeStr = "[";
		
		for(FunTreeNode node : treeNode.getChildNodes()){
			if(rootId.equals(node.getUpId())){
				// 处理图标 ---这块是平台的图标，不取这里的图标，取菜单描述中的配置项作为菜单的图标
//				String sicon = node.getSmallIcon();
//				if(sicon!=null &&!"".equals(sicon)){
//					/*if("xip".equalsIgnoreCase(node.getAppCode())){
//						sicon = "',icon:'"+request.getContextPath()+node.getSmallIcon();
//					}else{
//						sicon = "',icon:'"+node.getSmallIcon();
//					}*/
//					sicon = "',icon:'"+request.getContextPath()+node.getSmallIcon();
//				}else{
//					sicon = "',icon:'";
//				}				
//				
				//node = nodeNoEmpty(node);
				
				menuTreeStr+="{text:'"+node.getFuncName()+"',id:'"+node.getFunId()+"',url:'"+createNewUrl(node,par)+"',app_id:'"+node.getAppId()+"',app_code:'"+node.getAppCode()+"',fun_code:'"+node.getFunCode()+"',in_xip:'"+node.getInApp()+"',show_style:'"+node.getShowStyle()+"',";
				String iconClass = "a";//getIconClass(node.getFunId());//获取图标样式
				if("M".equals(node.getFunType())){
					//myIcon
					menuTreeStr+="leaf:false,icon:'"+ iconClass +"',child:"+parseFunTree(node, node.getMenuId(),request)+"},";
				}else{
					
					//menuTree+="leaf:true},";
					menuTreeStr+="leaf:true,icon:'"+ iconClass +"'},";
				}
			}
		}
		if(menuTreeStr.length()>1){
			menuTreeStr=menuTreeStr.substring(0,menuTreeStr.length()-1);
		}	
		menuTreeStr+="]";
		return menuTreeStr ;
	}
	
	public static String createNewUrl(FunTreeNode node,HashMap<String, String> param){
		String newUrl="" ;
		
		if("".equals(node.getFunUrl())||node.getFunUrl()==null){
			newUrl="";
		}else{
			if(node.getFunUrl().indexOf("?")>0){
				newUrl = node.getFunUrl()+"&";
			 }else{
			    newUrl = node.getFunUrl()+"?";
			}   
	        newUrl = newUrl+"userId="+param.get("userId")+"&roleId="+param.get("roleId")+"&funId="+node.getFunId()+"&appId="+node.getAppId()+"&userName="+param.get("userName")+"&roleCode="+param.get("roleCode")+"&funCode="+node.getFunCode()+"&appCode="+node.getAppCode();
		} 
		return newUrl;
	}

	public String mnuTransDataJson(String json,HashMap<String, String> sessionVars){
	//	String authority = ConfigLoadUtil.getSystemParamVal(PfConstants.AUTH_MODAL);
		
		JSONObject jo = new JSONObject();
		
		//jo.put("auth_modal", authority);
		jo.put("emp_name", sessionVars.get(SessionVariable.empName));
		jo.put("userId", sessionVars.get(SessionVariable.userId));
//		if("USER".equals(authority)){
//			
//		}else if("ROLE".equals(authority)){
			//List<Roles> roleList = mainFlService.getRoleList(sessionVars.get(SessionVariable.userId));
			HashMap<String, String> par = new HashMap<String, String>();
			par.put("role_id", sessionVars.get(SessionVariable.roleId));
			par.put("role_name", sessionVars.get(SessionVariable.roleName));
			jo.put("shRole",par);//当前显示的角色
		//	jo.put("roles", roleList);//用户拥有的角色
	//	}
		
//		String pwdLenLimit= ConfigLoadUtil.getSystemParamVal("PW_LEN_LIMIT");
//		jo.put("PW_LEN_LIMIT", pwdLenLimit);
//	    
//		String pwdIncludeChar = ConfigLoadUtil.getSystemParamVal("PW_INCLUDE_CHAR");
//		jo.put("PW_INCLUDE_CHAR", pwdIncludeChar);
		jo.put("mnuTreeData", json);
		return jo.toString();
    }




	@RequestMapping("/personalLogin")
    public void personalLogin(HttpServletRequest request, HttpServletResponse response){
		  JSONObject json = new JSONObject();
	        try {
	        	//String vcode = (String) request.getSession().getAttribute(ImageUtil.V_CODE);
//	        	if(StringUtil.isNullOrEmpty(vcode)){
//	        		 json.put("flag", "1");
//                     json.put("msg", "验证码失效，请刷新登录界面！");
//                     ConfigLoadUtil.outPrint(response, json.toString());
//                     return;
//	        	};
	        	String v_code = request.getParameter("vcode");
 	            String loginName = request.getParameter("username");
 	            System.out.println("---username:"+loginName);
	            if (loginName != null) {
 	                loginName = URLDecoder.decode(loginName, "UTF-8");
	            } else {
	                loginName = "";
	            }
	            String password = request.getParameter("password");
	            String reqUrl = request.getParameter("url");
	            if ((reqUrl != null) && (!"null".equals(reqUrl))) {
	                reqUrl = URLDecoder.decode(reqUrl, "UTF-8");
	            } else {
	                reqUrl = null;
	            }
	                boolean loginFlag = false;
	                User user = userService.getUserByName(loginName);
	                //不区分大小写
//	                if(!vcode.equalsIgnoreCase(v_code)){
//	                	json.put("flag", "1");
//	                	json.put("msg", "验证码错误！");
//	                } else 
	                	if (user == null) {
	                    json.put("flag", "1");
	                    json.put("msg", "用户不存在!");
	                } else {
	                    if (user.getPassword() == null) {
	                    	//去除密码校验
	                        json.put("flag", "1");
	                        json.put("msg", "密码不正确!");
	                    } else if (!user.getPassword().equals(password)) {
	                        //去除密码校验
	                    	json.put("flag", "1");
	                        json.put("msg", "密码不正确!");
	                    } else {
	                        if ("U".equals(user.getType())) {// 系统用户不用进行此校验
	                            long nowTime = new Date().getTime();
	                         // 生效时间和失效时间判断
	                            if (user.getsDate() == null && user.geteDate() == null) { 
	                                json.put("flag", "0");
	                                json.put("msg", "登录成功!");
	                                loginFlag = true;
	                            } else if (user.getsDate() != null && user.geteDate() == null) {
	                                if (nowTime < user.getsDate().getTime()) {
	                                    json.put("flag", "1");
	                                    json.put("msg", "该用户还未生效!");
	                                } else {
	                                    json.put("flag", "0");
	                                    json.put("msg", "登录成功!");
	                                    loginFlag = true;
	                                }
	                            } else if (user.getsDate() == null && user.geteDate() != null) {
	                                if (nowTime > user.geteDate().getTime()) {
	                                    json.put("flag", "1");
	                                    json.put("msg", "该用户已经失效!");
	                                } else {
	                                    json.put("flag", "0");
	                                    json.put("msg", "登录成功!");
	                                    loginFlag = true;
	                                }
	                            } else {
	                                if ((nowTime >= user.getsDate().getTime()) && (nowTime <= user.geteDate().getTime())) {
	                                    json.put("flag", "0");
	                                    json.put("msg", "登录成功!");
	                                    loginFlag = true;
	                                } else {
	                                    json.put("flag", "1");
	                                    json.put("msg", "该用户已经失效!");
	                                }
	                            }
	                        } else {
	                            json.put("flag", "0");
	                            json.put("msg", "登录成功!");
	                            loginFlag = true;
	                        }
	                    }
	                }
	      //      }

 	            System.out.println("---json:"+json.toString());
	            // 如果登录验证通过，则进行登录初始化
	            if ("0".equals(json.get("flag"))) {
	                String serverUrl = ConfigLoadUtil.getAcConfigVal("serverUrl");
	                String clientUrl = ConfigLoadUtil.getAcConfigVal("serverName");
	                LoginInitUtil.loginInit(request, response, null, loginName, "ac", true, true);
	                HashMap<String, String> sessionVars = (HashMap<String, String>) request.getSession().getAttribute(SessionVariable.SESSION_VARS);
	                String userId = sessionVars.get(SessionVariable.userId);
	                String authority = sessionVars.get(SessionVariable.AUTH_MODAL);
	                String loginId = sessionVars.get(SessionVariable.recordLoginId);
	              //  boolean isMobile = ConfigLoadUtil.isMoblie(request);
	                String url = reqUrl;
	                if (url == null || "".equals(url.trim())) {
//	                    if (isMobile) {
//	                      //  url = clientUrl + "/main?xwl=23W2EADAWUTR";
//	                    	url = clientUrl + "/frame/navigator.jsp";
//	                    } else {
	                	   if(clientUrl == null || "null".equals(clientUrl)) {
	                		   url = "/index.html";
	                	   }else {
	                		   url = clientUrl + "/index.html";
	                	   }
	                        
	                  //  }
	                }
	                if (url.indexOf("?") > 0) {
	                    url = url + "&ticket=" + loginId;
	                } else {
	                    url = url + "?ticket=" + loginId;
	                }
	                // 是否重置了密码
	                String isResetPassword = sessionVars.get(SessionVariable.isResetPassword);
	                // 判断密码是否过期
	                String isIneffective = sessionVars.get(SessionVariable.isIneffective);
	                String redirectUrl = null;
//	                if ("USER".equals(authority)) {
//	                    if ("true".equalsIgnoreCase(isResetPassword) || "true".equalsIgnoreCase(isIneffective)) {
//	                        redirectUrl = serverUrl + "/frame/password.jsp?url=" + URLEncoder.encode(url, "UTF-8");
//	                    }
//	                }
//	                if ("ROLE".equals(authority)) {
	                    String hasDefineDefaultRole = sessionVars.get(SessionVariable.hasDefineDefaultRole); // 是否定义了默认角色
	                        if (!"true".equalsIgnoreCase(hasDefineDefaultRole)) {
	                            redirectUrl = serverUrl + "/frame/editRole.jsp?url=" + URLEncoder.encode(url, "UTF-8");
	                        } else if ("true".equalsIgnoreCase(hasDefineDefaultRole)
	                                && ("true".equalsIgnoreCase(isResetPassword) || "true".equalsIgnoreCase(isIneffective))) {
	                            redirectUrl = serverUrl + "/frame/password.jsp?url=" + URLEncoder.encode(url, "UTF-8");
	                        }
	               // }
	                if (redirectUrl == null) {
	                    redirectUrl = url;
	                }
	                request.getSession().setAttribute(SessionVariable.SESSION_VARS, sessionVars);
	              
//	                OptionModel option = optService.selectByUser(CurrentSessionVar.getUserId());
//	                if(StringUtils.isEmpty(option.getUser_id()) ){
//	                	request.getSession().setAttribute("themeSkin", option.getSkin());
//	                }
//	                request.getSession().setAttribute("emp_id", option.getEmp_id());
//	                request.getSession().setAttribute("emp_name", option.getEmp_name());
	                json.put("flag", "0");
	                json.put("msg", "登录成功!");
	                json.put("url", redirectUrl);
	            }
 	            System.out.println("---json:"+json.toString());
	            ConfigLoadUtil.outPrint(response, json.toString());
	        } catch (Exception e) {
	            log.error(new Exception().getStackTrace()[0], e);
	            json.put("flag", "-1");
	           // json.put("msg", e.getMessage());
	            json.put("msg", "系统登录异常！");
 	            System.out.println("---error-json:"+json.toString());
	            ConfigLoadUtil.outPrint(response, json.toString());
	        }
    }
	
	/**
	 * 
	 * logout:退出系统
	 */
//	@RequestMapping(params = "method=logout", method = RequestMethod.POST)
//    public @ResponseBody Map<String, String> logout(HttpServletRequest request, HttpServletResponse response) {
//        Map<String, String> map = new HashMap<String, String>();
//        HttpSession session = request.getSession();
//        Cookie[] cookies = request.getCookies();
//        String loginId=null;
//        String encServerUrl = "";
//        String casServerUrl = ConfigLoadUtil.getServer();
//        try {
//            encServerUrl = URLEncoder.encode(casServerUrl, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            encServerUrl = "";
//        }
//        for(Cookie cookie:cookies){
//            if (("Xz.LoginId."+encServerUrl).equals(cookie.getName())) {
//                loginId = cookie.getValue();
//                break;
//            }
//        }
//        Cookie loginCookie = new Cookie("Xz.Login."+encServerUrl, "");
//        loginCookie.setPath("/");
//        response.addCookie(loginCookie);
//        Cookie loginIdCookie = new Cookie("Xz.LoginId."+encServerUrl, "");
//        loginIdCookie.setPath("/");
//        response.addCookie(loginIdCookie);
//        String useCasFlag = ConfigLoadUtil.getSystemParamVal(PlatformConstants.USE_CAS_FLAG);
//        HashMap<String, Object> hashmap = (HashMap<String, Object>) session.getAttribute(SessionVariable.SESSION_VARS);
//        String userName =(String) hashmap.get(SessionVariable.userName);  
//        String userId =(String)hashmap.get(SessionVariable.userId);
//        ConfigLoadUtil.removeUserRole(userId);
//        ConfigLoadUtil.removeLoginLog(userName);
//        String clientServerUrl = ConfigLoadUtil.getCasClient();
//        if ("Y".equals(useCasFlag)) {
//            try {
//                // 取得配置文件值
//                // Tomcat CAS登出地址
//                String logoutUrl = casServerUrl.concat("/logout");
//
//                // Tomcat CAS 登出后回调地址
//                String serverName = clientServerUrl.replace("//", "");
//                String[] a = serverName.split(":");
//                String ctxPath = request.getContextPath().replace("\\", "").replace("/", "");
//                String callbackUrl = "?service=" + a[0] + "%3A%2F%2F" + a[1] + "%3A" + a[2] + "%2F" + ctxPath + "%2F";
//
//                // 返回值
//                map.put("flag", "1");
//                map.put("logoutUrl", logoutUrl);
//                map.put("callbackUrl", callbackUrl);
//                map.put("msg", "");
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            if (session != null)
//                session.invalidate();
//
//            return map;
//        } else {
//
//            try {
//                ConfigLoadUtil.delAppLogin(userName); 
//                // 取得配置文件值
//                if(!casServerUrl.trim().equalsIgnoreCase(clientServerUrl.trim())){
//                    String logoutUrl = casServerUrl.concat("/platform.do?method=delLoginLog&userName=").concat(userName);
//                    JSONObject json = ConfigLoadUtil.sendGetRequest(logoutUrl);
//                }
//                map.put("flag", "1");
//                if (ConfigLoadUtil.isMoblie(request)) {
//                    map.put("logoutUrl", casServerUrl + "/m?xwl=xip/login/login&keyCode="+URLEncoder.encode(clientServerUrl,"UTF-8"));
//                } else {
//                    String loginUrl = ConfigLoadUtil.getSystemParamVal("LOGIN_PAGE_URL");
//                    if ((loginUrl == null) || ("".equals(loginUrl.trim()))) {
//                        loginUrl = casServerUrl + "/m?xwl=xip/login/login";
//                    }
//                    if(loginUrl.indexOf("?")>0){
//                        loginUrl=loginUrl.concat("&keyCode=").concat(URLEncoder.encode(clientServerUrl,"UTF-8"));
//                    }else{
//                        loginUrl=loginUrl.concat("?keyCode=").concat(URLEncoder.encode(clientServerUrl,"UTF-8"));
//                    }
//                    map.put("logoutUrl", loginUrl);
//                }
//                map.put("callbackUrl", "");
//                map.put("msg", "");
//                if (session != null)
//                    session.invalidate();
//                return map;
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                return map;
//            }
//        }
//
//    }

	/**
	 * 
	 * modifyPassword:更改密码,当用户登录成功后判断用户密码是否为初始密码如果为初始密码的话需要用户更改密码
	 */
//	@RequestMapping(params = "method=modifyPassword", method = RequestMethod.POST)
//	public @ResponseBody Map<String, String> modifyPassword(HttpServletRequest request) {
//
//		HttpSession session = request.getSession();
//
//		String userId = CurrentSessionVar.getUserId(session);
//
//		String password = request.getParameter("password");
//
//		Map<String, String> map = new HashMap<String, String>();
//		User user = new User();
//		user.setUserId(userId);
//		user.setPassword(password);
//		user.setLastChgPwdDate(new Date());
//
//		try {
//			systemService.modifyPassword(user);
//
//			// 获取导航模式
//			String model = ConfigLoadUtil.getSystemParamVal(PlatformConstants.NAVIGATOR_MODEL);
//			if ("MENU".equals(model)) { // 菜单模式
//				map.put("url", "/frame/main.jsp");
//
//			} else if ("TREE".equals(model)) { // 树形模式
//				map.put("url", "/main?xwl=23VRM7XF4LFO");
//			}
//
//			map.put("flag", "1");
//			map.put("msg", "");
//
//		} catch (Exception e) {
//
//			map.put("flag", "2");
//			map.put("msg", e.getMessage());
//		}
//
//		return map;
//
//	}



//	@SuppressWarnings("rawtypes")
//	@RequestMapping(params = "method=loadMenuTree")
//	public void loadMenuTree(HttpServletRequest request, HttpServletResponse response) {
//		response.setContentType("text/html;charset=utf-8");
//		Parameter parameter = ParameterParser.parseParams(request);
//		HashMap<String, String> sessionVars = (HashMap<String, String>) request.getSession().getAttribute(SessionVariable.SESSION_VARS);
//
//		// 如果启用redis, 则从redis数据库中获取数据
////		if("true".equalsIgnoreCase(ConfigLoadUtil.useRedis)){
////			sessionVars.put(SessionVariable.roleId, ConfigLoadUtil.getUserRole(sessionVars.get(SessionVariable.userId))) ;
////		}
//
//		// 数据查询SQL语句中使用的递归
//		// ArrayList<HashMap>
//		// recordMap=authorityMgrService.loadNavigatTree(sessionVars);
//		// String s = parseMenuTree(recordMap,"0");
//
//		// java方法中使用的递归
//		TreeNode treeNode = authorityMgrService.loadMenuTree(sessionVars);
//		String s = parseMenuTreeFromTreeNode(treeNode, "0", request);
//
//		// JSONArray str = JSONArray.fromObject(recordMap);
//		// System.out.println(recordMap.size());
//		// System.out.println(s);
//
//		// 设置编码格式
//		// rData=rData.replaceAll("\\n", "");
//		// rData=rData.replaceAll("\\r", "");
//		response.setContentType("text/html;charset=utf-8");
//		try {
//			// 输出JSON串
//			response.getWriter().write(s);
//			response.getWriter().close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		// return s;
//	}

//	@SuppressWarnings("unused")
//	private String parseMenuTree(ArrayList<HashMap<String,Object>> al, String rootId) {
//		String menuTree = "[";
//		HashMap<String,Object> mb = new HashMap<String,Object>();
//		for (int i = 0; i < al.size(); i++) {
//			mb = (HashMap<String,Object>) al.get(i);
//			String up_id = (String) mb.get("UP_ID");
//			if (up_id.equals(rootId)) {
//				menuTree += "{text:'" + mb.get("MNU_NAME") + "',ignoreParentClicks:true,";
//				if ("M".equals(mb.get("NODE_TYPE"))) {
//					String listeners = "";
//					if ("0".equals(rootId)) {
//						listeners = "listeners:{'mouseover':mouseOverEvent},";
//					}
//					menuTree += "hideOnClick :false," + listeners + "menu:" + parseMenuTree(al, (String) mb.get("MNU_ID")) + "},";
//				} else {
//					String sicon = (String) mb.get("SMALL_ICON");
//					if (sicon != null && !"".equals(sicon)) {
//						sicon = "',icon:'" + mb.get("SMALL_ICON");
//					} else {
//						sicon = "";
//					}
//					menuTree += "listeners:{'click':clickFunc},fun_id:'" + mb.get("FUN_ID") + sicon + "',url:'" + mb.get("URL") + "',app_id:'" + mb.get("APP_ID") + "',fun_code:'" + mb.get("FUN_CODE")
//							+ "',app_code:'" + mb.get("APP_CODE") + "'},";
//					// menuTree+="url:'"+"'},";
//				}
//			}
//		}
//		if (menuTree.length() > 1) {
//			menuTree = menuTree.substring(0, menuTree.length() - 1);
//		}
//		menuTree += "]";
//		return menuTree;
//	}

	/**
	 * @Title: parseMenuTreeFromTreeNode 
	 * @Description:  
	 * @param treeNode
	 * @param rootId
	 * @param request
	 * @return String    返回类型
	 */
//	private String parseMenuTreeFromTreeNode(TreeNode treeNode, String rootId, HttpServletRequest request) {
//		String menuTree = "[";
//
//		for (TreeNode childNode : treeNode.getChildNodes()) {
//			if ("M".equals(childNode.getNodeType())) {
//				menuTree += "{text:'" + childNode.getMenuName() + "',ignoreParentClicks:true,";
//				String listeners = "";
//				if ("0".equals(rootId)) {
//					listeners = "listeners:{'mouseover':mouseOverEvent},";
//				}
//				menuTree += "hideOnClick :false," + listeners + "menu:" + parseMenuTreeFromTreeNode(childNode, childNode.getMenuId(), request) + "},";
//			} else {
//				menuTree += "{text:'" + childNode.getFuncName() + "',ignoreParentClicks:true,";
//				String sicon = childNode.getSmallIcon();
//				if (sicon != null && !"".equals(sicon)) {
//					/*
//					 * if ("xip".equalsIgnoreCase(childNode.getAppCode())) {
//					 * sicon = "',icon:'" + request.getContextPath() +
//					 * childNode.getSmallIcon(); } else { sicon = "',icon:'" +
//					 * childNode.getSmallIcon(); }
//					 */
//					sicon = "',icon:'" + request.getContextPath() + childNode.getSmallIcon();
//				} else {
//					sicon = "";
//				}
//				menuTree += "listeners:{'click':clickFunc},fun_id:'" + childNode.getFunId() + sicon + "',funUrl:'" + childNode.getUrl() + "',app_id:'" + childNode.getAppId() + "',fun_code:'"
//						+ childNode.getFunCode() + "',app_code:'" + childNode.getAppCode() + "',in_xip:'" + childNode.getInXip() + "',show_style:'"+childNode.getShowStyle()+"'},";
//				// menuTree+="url:'"+"'},";
//			}
//
//		}
//
//		if (menuTree.length() > 1) {
//			menuTree = menuTree.substring(0, menuTree.length() - 1);
//		}
//
//		menuTree += "]";
//		return menuTree;
//	}

	/**
	 * @Title: getUserFavoriteFuntions 
	 * @Description:  获取用户收藏夹信息
	 * @param request
	 * @param response
	 * @return void    返回类型
	 */
//	@RequestMapping(params = "method=getUserFavoriteFuntions", method = RequestMethod.POST)
//	public static void getUserFavoriteFuntions(HttpServletRequest request, HttpServletResponse response) {
//		// 取得session变量信息
//		Parameter parameter = ParameterParser.parseParams(request);
//		HashMap<String, String> sessionVars = (HashMap<String, String>) request.getSession().getAttribute(SessionVariable.SESSION_VARS);
//
//		String userId = request.getParameter("userId");
//
//		HashMap params = new HashMap();
//		params.put("userId", userId);
//
//		CommonWB wb = new CommonWB();
//		String json = wb.getUserFavoriteFuntions(params, request.getContextPath());
//
//		// 输出树形结构
//		response.setContentType("text/html;charset=utf-8");
//		try {
//			response.getWriter().write(json);
//			response.getWriter().close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * @Title: getIcons 
	 * @Description: 获取图标
	 * @param request
	 * @param response
	 * @return void    返回类型
	 */
//	@RequestMapping(params = "method=getIcons", method = RequestMethod.POST)
//	public static void getIcons(HttpServletRequest request, HttpServletResponse response) {
//
//		String fpath = "";
//		String iconType = request.getParameter("iconType");
//		
//		if("smallIcon".equals(iconType)){
//			fpath = "pub/uip/common/image";
//		}else if("largeIcon".equals(iconType)){
//			fpath = "pub/uip/common/icon";
//		}
//		
//
//		
//		String path = request.getRealPath("/");
//		
//		String contextPath = request.getContextPath();
//		String filepath = path + fpath;
//		
//		String json = "";
//		StringBuffer rows = new StringBuffer("");
//		String metaData = "{'fields':["
//				+ "{'name':'NAME','type':'string'},"
//				+ "{'name':'URL','type':'string'},"
//				+ "{'name':'SIZE','type':'string'},"
//				+ "{'name':'LASTMOD','type':'string'}]}";
//
//		try {
//			File file = new File(filepath);
//			if (!file.isDirectory()) {
//				json = JSONUtil.parseStoreJsonForExt4(0, "true", metaData, "");
//			} else if (file.isDirectory()) {
//				String[] filelist = file.list();
//				for (int i = 0; i < filelist.length; i++) {
//					File readfile = new File(filepath + File.separatorChar+ filelist[i]);
//					if (!readfile.isDirectory()) {
//						DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//						String imgName = readfile.getName();
//						// String imgUrl = readfile.getPath();
//						String imgUrl = contextPath + "/" + fpath + "/" + imgName;
//						Long imgSize = readfile.length();
//						Long lastMod = readfile.lastModified();
//						Date d = new Date(lastMod);
//
//						rows.append("{NAME:'").append(imgName).append("',URL:'");
//						rows.append(imgUrl).append("',SIZE:'").append(imgSize).append("',LASTMOD:'");
//						rows.append(format.format(d)).append("'}");
//						if (i == filelist.length - 1) {
//						} else {
//							rows.append(",");
//						}
//					}
//				}
//				String row = rows.toString();
//				json = JSONUtil.parseStoreJsonForExt4(filelist.length, "true", metaData, row);
//			}
//		} catch (Exception e) {
//			System.out.println("readfile() Exception:" + e.getMessage());
//		}
//
//		// 输出树形结构
//		response.setContentType("text/html;charset=utf-8");
//		try {
//			response.getWriter().write(json);
//			response.getWriter().close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * @Title: modifyUserPassword 
	 * @Description: 更改密码,当用户登录成功后判断用户密码是否为初始密码如果为初始密码的话需要用户更改密码
	 * @param request
	 * @return Map<String,String>    返回类型
	 */
//	@RequestMapping(params = "method=modifyUserPassword", method = RequestMethod.POST)
//	public @ResponseBody Map<String, String> modifyUserPassword(HttpServletRequest request) {
//		// 返回信息
//		Map<String, String> map = new HashMap<String, String>();
//
//		// 获取会话参数信息
//		HttpSession session = request.getSession();
//		HashMap<String, String> sessionVars = (HashMap<String, String>) session.getAttribute(SessionVariable.SESSION_VARS);
//		String userId = CurrentSessionVar.getUserId(session);
//
//		String newPassword = request.getParameter("password"); // 新密码
//		String oldPassword = request.getParameter("oldPwd"); // 原密码
//		
//		// 封装用户
//		User user = new User();
//		user.setUserId(userId);
//		user.setOldPassword(oldPassword);
//		user.setPassword(newPassword);
//		user.setLastChgPwdDate(new Date());
//		
//		String isValidPassword = request.getParameter("isValidPassword");//用于标示是否对源密码进行校验：N校验，其它不校验
//		if("N".equals(isValidPassword)){
//			user.setIsValidPassword("N");
//		}
//
//		String useCasFlag = ConfigLoadUtil.getSystemParamVal(PlatformConstants.USE_CAS_FLAG);
//		if ("Y".equals(useCasFlag)) {
//			try {
//				// 执行密码修改
//				systemService.modifyPassword(user);
//				sessionVars.put(SessionVariable.isResetPassword, Boolean.toString(false));
//				sessionVars.put(SessionVariable.isIneffective, Boolean.toString(false));
//				/*
//				 * // Tomcat CAS登出地址 String logoutUrl =
//				 * session.getServletContext().getInitParameter("logoutUrl");
//				 * 
//				 * // Tomcat CAS 登出后回调地址 String appHostName =
//				 * session.getServletContext().getInitParameter("appHostName");
//				 * String appPort =
//				 * session.getServletContext().getInitParameter("appPort");
//				 * String appContext =
//				 * session.getServletContext().getInitParameter("appContext");
//				 * String callbackUrl = "?service=http%3A%2F%2F" + appHostName +
//				 * "%3A" + appPort + "%2F" + appContext + "%2F";
//				 */
//
//				// 取得配置文件值
//				// Tomcat CAS登出地址
//				String logoutUrl = ConfigLoadUtil.getAcConfigVal("serverUrl").concat("/logout");
//
//				// Tomcat CAS 登出后回调地址
//				String serverName = ConfigLoadUtil.getAcConfigVal("serverName").replace("//", "");
//				String[] a = serverName.split(":");
//				String ctxPath = request.getContextPath().replace("\\", "").replace("/", "");
//				String callbackUrl = "?service=" + a[0] + "%3A%2F%2F" + a[1] + "%3A" + a[2] + "%2F" + ctxPath + "%2F";
//
//				// 提示信息
//				map.put("flag", "1");
//				map.put("logoutUrl", logoutUrl);
//				map.put("callbackUrl", callbackUrl);
//				map.put("msg", "密码修改成功！");
//
//				// if(session!=null)session.invalidate();
//				return map;
//
//			} catch (Exception e) {
//				e.printStackTrace();
//				map.put("flag", "2");
//				map.put("msg", e.getMessage());
//				return map;
//			}
//		} else {
//			try {
//				// 执行密码修改
//				systemService.modifyPassword(user);
//				sessionVars.put(SessionVariable.isResetPassword, Boolean.toString(false));
//                sessionVars.put(SessionVariable.isIneffective, Boolean.toString(false));
//				// 取得配置文件值
//				String serverName = ConfigLoadUtil.getXipConfigVal("cas.serverName");
//				map.put("flag", "1");
//				map.put("logoutUrl", serverName + request.getContextPath() + "/login/login.jsp");
//				map.put("callbackUrl", "");
//				map.put("msg", "密码修改成功！");
//				// if (session != null)session.invalidate();
//				return map;
//			} catch (Exception e) {
//				e.printStackTrace();
//				map.put("flag", "2");
//				map.put("msg", e.getMessage());
//				return map;
//			}
//		}
//	}

//	@RequestMapping(params = "method=getRolesByUserId")
//	public void getRolesByUserId(HttpServletRequest request, HttpServletResponse response) {
//		String userId = request.getParameter("userId");
//
//		List<HashMap> roles = systemService.getAllRolesByUserId(userId);
//
//		int size = 0;
//
//		String rows = "";
//
//		if (roles != null) {
//			size = roles.size();
//			for (HashMap role : roles) {
//				rows = rows + "{'roleId':'" + role.get("ROLE_ID") + "','roleCode':'" + role.get("ROLE_CODE") + "','roleName':'" + role.get("ROLE_NAME") + "'},";
//			}
//
//			if (rows.lastIndexOf(",") == rows.length() - 1) {
//				rows = rows.substring(0, rows.length() - 1);
//			}
//		}
//
//		StringBuffer json = new StringBuffer("{");
//
//		json.append("'total':'").append(size).append("',");
//		json.append("'success':true,");
//		json.append("'metaData':").append("{'fields':[{'name':'roleId','type':'string'},{'name':'roleCode','type':'string'},{'name':'roleName','type':'string'}]}").append(",");
//		json.append("'rows':[").append(rows).append("]");
//		json.append("}");
//
//		// 设置编码格式
//		// rData=rData.replaceAll("\\n", "");
//		// rData=rData.replaceAll("\\r", "");
//		response.setContentType("text/html;charset=utf-8");
//		try {
//			response.getWriter().write(json.toString());
//			response.getWriter().close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	@RequestMapping("/getVcode")
	@ResponseBody
	public void getVcode(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("image/jpeg");
		ServletOutputStream sos = response.getOutputStream();
		Map<String, BufferedImage> nuff = ImageUtil.createImage();
		BufferedImage img = null ;
		String v_code = null;
		for(String code:nuff.keySet()){
			img = nuff.get(code);
			v_code = code;
		}
		request.getSession().setAttribute(ImageUtil.V_CODE,v_code);
		ImageIO.write(img, "jpeg", sos);
		sos.close();
	}
	
	@RequestMapping("/addVisit")
	@ResponseBody
	public void addVisit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		LoginInitUtil.addLoginLog(request,response, null, "ADMIN",
				"visitor");
	}
}
