package com.platform.au.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platform.au.entity.User;
import com.platform.au.service.UserService;
import com.platform.common.page.JsonVo;
import com.platform.common.page.TabPage;

@Controller
@RequestMapping("/we/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@RequestMapping("/listData")
	@ResponseBody
	public TabPage<User> listData(HttpServletRequest request, HttpServletResponse response) {
       response.setCharacterEncoding("UTF-8");
       TabPage<User> jsonVo=new TabPage<User>();
		try {
		//	AcSwitch.setDataSourceType("dataSource0");
			Map<String, Object>param = new HashMap<String, Object>();
			param.put("limit", Integer.valueOf(request.getParameter("limit")));
			param.put("offset", (Integer.valueOf(request.getParameter("page")) - 1)*Integer.valueOf(request.getParameter("limit")));
			jsonVo = userService.selectForPage(param);
		} catch (Exception e) {
			e.printStackTrace();
			//log.error(new Exception().getStackTrace()[0], e);
//			jsonVo.setFlag("1");
//			jsonVo.setMsg(e.getMessage());
		}
		return jsonVo;
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public JsonVo<List<User>> findAll(HttpServletRequest request, HttpServletResponse response) {
       response.setCharacterEncoding("UTF-8");
       JsonVo<List<User>> jsonVo=new JsonVo<List<User>>();
		try {
			Map<String, Object>param = new HashMap<String, Object>();
			jsonVo.setObj(userService.findAll(param));
			jsonVo.setFlag("0");
			jsonVo.setMsg("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			//log.error(new Exception().getStackTrace()[0], e);
//			jsonVo.setFlag("1");
//			jsonVo.setMsg(e.getMessage());
		}
		return jsonVo;
	}
}
