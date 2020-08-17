package com.platform.au.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.platform.au.entity.User;
import com.platform.common.dao.BaseDao;


public interface UserDao extends BaseDao<User>{
  
	public String getUserIdByName(String userName);
	
	public String getUserNameById(String userId);

	public String getUserEmpNameById(String userId);
	
	
	public User getUserByName(String userName);
	

	public HashMap<String,Object> getUsers(List<String> users) throws Exception ;
	
	public User getUserById(String userId)throws Exception ;

	public List<Map> getEmpIdByEmpCode(String empCode);

	public List<Map> getUserByEmpId(String empId);
}
