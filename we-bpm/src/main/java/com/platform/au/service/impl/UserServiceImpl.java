package com.platform.au.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platform.au.dao.UserDao;
import com.platform.au.entity.User;
import com.platform.au.service.UserService;
import com.platform.common.dao.BaseDao;
import com.platform.common.service.impl.BaseServiceImpl;
import com.platform.util.JSONUtil;

@Service("userServiceImpl")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{

	@Autowired
	private UserDao userDao;

	@Override
	public BaseDao<User> getDao() throws Exception {
		// TODO Auto-generated method stub
		return userDao;
	} 
	
	@Override
	public String getUserIdByName(String userName) {
		return userDao.getUserIdByName(userName);
	}

    @Override
    public String getUserNameById(String userId) {
        return userDao.getUserNameById(userId);
    }

	@Override
	public User getUserByName(String userName) {
		return userDao.getUserByName(userName);
	}

    @Override
    public String getUserEmpNameById(String userId) {
        return userDao.getUserEmpNameById(userId);
    }

    @Override
	public List<Map> getEmpIdByEmpCode(String empCode) {
    	List<Map> result = userDao.getEmpIdByEmpCode(empCode);
		return result;
		
	}
   
	@Override
	public String getUserByEmpId(String empId) {
		List<Map> result = userDao.getUserByEmpId(empId);
    	String json = JSONUtil.beanListToJson(result, null);
		if(result.size()==0){
			json="";
		}
		return json;
	}

	

	
}
