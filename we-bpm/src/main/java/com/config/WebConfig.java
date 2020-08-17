/**
 * @Title: WebConfig.java
 * @Package com.we.ac.platform.config
 * @Description: 
 * Copyright: Copyright (c) 2018
 * Website: www.panzhijie.cn
 * 
 * @Author gzl
 * @DateTime 2019年9月21日 下午10:42:14
 * @version V1.0
 */

package com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.platform.interceptor.LoginValidationInterceptor;

/**
 * @ClassName: WebConfig
 * @Description: 
 * @Author gzl
 */

@Configuration
public class WebConfig implements WebMvcConfigurer  {
	 
		
	 
		/**
		 * 不需要登录拦截的url
		 */
		final String[] notLoginInterceptPaths = {"/static/**","/admin/login","/error/**","/login"};
	 
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			// 这里添加多个拦截器
			// 登录拦截器
			registry.addInterceptor(new LoginValidationInterceptor()).addPathPatterns("/**")
			          .excludePathPatterns(notLoginInterceptPaths);
		}
	 
		@Override
		public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
			configurer.enable();
		}
	 
	 
		/**
		 * 配置不需要经过controller就跳转到登录页面
		 */
		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addViewController("/login").setViewName("login");
	 
		}
 
		/***
		 * addResourceLocations指的是文件放置的目录，addResoureHandler指的是对外暴露的访问路径
		 */
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			// TODO Auto-generated method stub
			//排除静态资源拦截
			registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
			WebMvcConfigurer.super.addResourceHandlers(registry);
		}
		
}
