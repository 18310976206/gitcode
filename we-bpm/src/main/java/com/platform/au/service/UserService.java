package com.platform.au.service;

import java.util.List;
import java.util.Map;

import com.platform.au.entity.User;
import com.platform.common.service.BaseService;



public interface UserService extends BaseService<User>{
	
	/**
	 * 
	 * 根据用户名称取得userId
	 */
	public String getUserIdByName(String userName);
	
    /**
     * 根据用户ID获取用户名
    */
    public String getUserNameById(String userId);
    /**
     * 根据用户ID获取用户的员工姓名
    */
    public String getUserEmpNameById(String userId);
    
    /**
     *根据用户名称取得用户信息
     */
    public User getUserByName(String userName);

	public List<Map> getEmpIdByEmpCode(String empCode);

	public String getUserByEmpId(String getEmpId);

	
	
}
