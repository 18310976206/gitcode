package com.platform.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.platform.session.CustomSessionContext;
import com.platform.session.SessionVariable;
import com.platform.util.ConfigLoadUtil;
import com.platform.util.LoginInitUtil;

/**
 * 
 * ClassName:LoginValidationFilter Function:
 *  登录验证过滤器 ,判断是否存在默认职责,判断用户密码是否重置
 *
 *
 */
public class LoginValidationInterceptor implements HandlerInterceptor {
	//private static Logger logger = Logger.getLogger(LoginValidationInterceptor.class);
    private static List<String> noFilterUrlList = null; // 不拦截的url页面
    private static List<String> nolyPfUseUrlList = null; // 只有平台可以使用页面

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
          noFilterUrlList = ConfigLoadUtil.getFilterExclusion();
          nolyPfUseUrlList = ConfigLoadUtil.getOnlyAcUseUrl();
          String requestUrl = request.getRequestURL().toString();
          String queryStr = request.getQueryString();
          if (queryStr != null) {
              requestUrl = requestUrl.concat("?").concat(queryStr);
          }
          String lowerUrl = requestUrl.toLowerCase();
          if (lowerUrl.endsWith(".css") || lowerUrl.endsWith(".js") || lowerUrl.endsWith(".gif") || lowerUrl.endsWith(".jpg")
                  || lowerUrl.endsWith(".jpeg") || lowerUrl.endsWith(".png") || lowerUrl.endsWith(".ico") || lowerUrl.indexOf("fonts") > 0) {
              return true;
          }
          HttpSession session = request.getSession();
          CustomSessionContext.setCurrentSession(session);
          // 如果是Web Service（url中包含/services/的请求URL）都不校验登录信息
          if (requestUrl.indexOf("/services/") > 0) {
              return true;
          }
          HashMap<String, String> sessionVars = (HashMap<String, String>) session.getAttribute(SessionVariable.SESSION_VARS);
          String secCode = request.getParameter("secCode");
          // 如果有安全码，并且安全码有效，则模拟登录
          if ((secCode != null) && (secCode.length() > 0)) {
        	  //do....授权码待配置
              if ("activity-bpm".equals(secCode)) {
            	  String userCode = request.getParameter("userCode");
            	  LoginInitUtil.initSession(request, response, null, userCode, "sec", true);
            	  return true;
              }
          }
          for (String filterUrl : noFilterUrlList) {
              if (requestUrl.indexOf(filterUrl) >= 0) {
                  return true;
              }
          }
          for (String authUrl : nolyPfUseUrlList) {
              if (requestUrl.indexOf(authUrl) >= 0) {
                      return true;
              }
          }
          // 如果不存在userId的话说明还未登录
          if ((sessionVars == null) || (sessionVars.get(SessionVariable.userId) == null)) {
                  // 如果是登录页面，则跳过，否则跳转到登录页面
                  if (request.getRequestURI().indexOf(("/login.html")) >= 0) {
                      return true;
                  }
                  String serverUrl = ConfigLoadUtil.getServer();
                  sendRedirect(response,serverUrl + "/login.html");
          }
		return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
    
    //对于请求是ajax请求重定向问题的处理方法
    private static void sendRedirect(HttpServletResponse response, String redirectUrl){
        try {
         //这里并不是设置跳转页面，而是将重定向的地址发给前端，让前端执行重定向
            //设置跳转地址
            response.setHeader("redirectUrl", redirectUrl);
            //设置跳转使能
            response.setHeader("enableRedirect","true");
            response.flushBuffer();
        } catch (IOException ex) {
           // logger.error("Could not redirect to: " + redirectUrl, ex);
           ex.printStackTrace();
        }
    }

   
}
