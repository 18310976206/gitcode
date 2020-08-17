package com.platform.session;

import javax.servlet.http.HttpSession;

/**
 * ClassName:CustomSessionContext
 * Function: 自定义Session上下文环境
 *
 */
public class CustomSessionContext {
	static ThreadLocal<HttpSession> currentSession= new ThreadLocal<HttpSession>();
	
    public static synchronized HttpSession getCurrentSession() {
        return currentSession.get();
    }
    public static synchronized void setCurrentSession(HttpSession session) {
        currentSession.set(session);
    }
}
