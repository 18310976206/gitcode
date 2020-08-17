package com.platform.au.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.platform.au.dao.AuDao;
import com.platform.au.entity.FunTreeNode;
import com.platform.au.entity.Role;
import com.platform.au.entity.User;
import com.platform.au.entity.UserRole;
import com.platform.au.service.AuService;
import com.platform.util.JSONUtil;

@Service("auServiceImpl")
public class AuServiceImpl implements AuService{
	@Autowired
	private AuDao auDao;

	@Override
	public String getValueSetDtlList(String valueSetCode) {
		List<HashMap> result = auDao.getValueSetDtlList(valueSetCode);
		String json = JSONUtil.beanListToJson(result, null);
		if(json==null){
			json="";
		}
		return json;
	}

	@Override
	public String getParamList(String paramCode, String targetId) {
		List<HashMap> result = auDao.getParamList(paramCode, targetId);
		String json = JSONUtil.beanListToJson(result, null);
		if(json==null){
			json="";
		}
		return json;
	}

	@Override
	public String getSecOrgList(String targetId) {
		List<HashMap> result = auDao.getSecOrgList(targetId);
		String json = JSONUtil.beanListToJson(result, null);
		if(json==null){
			json="";
		}
		return json;
	}

	@Override
	public String getUserList() {
		List<HashMap> result = auDao.getUserList();
		String json = JSONUtil.beanListToJson(result, null);
		if(json==null){
			json="";
		}
		return json;
	}

	

	@Override
	public String getEmpList() {
		List<HashMap> result = auDao.getEmpList();
		String json = JSONUtil.beanListToJson(result, null);
		if(json==null){
			json="";
		}
		return json;
	}


	@Override
	public List<HashMap> getUserFavoriteFuntions(HashMap params) {
		return auDao.getUserFavoriteFuntions(params);
	}


	@Override
	public List<HashMap> getUserFunTreeByUserId(HashMap<String, String> map) {
		return auDao.getUserFunDataByUserId(map);
	}
	
	/*
     * wangwh
     */
	@Override
    public List<HashMap> getPassWordListByUserName(String userName){
    	return auDao.getPassWordListByUserName(userName);
    }
    @Override
    public void updatePassWord(String passWord,String userId){
    	HashMap map = new HashMap();
    	map.put("passWord", passWord);
    	map.put("newDate", new Date());
    	map.put("userId", userId);
    	auDao.updatePassWord(map);
    	
    }

	@Override
	public String empCodeValidation(String empCode) {
		List<HashMap> result = auDao.empCodeValidation(empCode );
		String json = JSONUtil.beanListToJson(result, null);
		if(result.size()==0){
			json="";
		}
		return json;
	}

	@Override
	public String userNameValidation(String userName) {
		List<HashMap> result = auDao.userNameValidation(userName);
		String json = JSONUtil.beanListToJson(result, null);
		if(result.size()==0){
			json="";
		}
		return json;
	}

	@Override
	public void updateStatus(String userId) {
		
		
		//取20年之后的日期
		Calendar calendar = Calendar.getInstance();
	        Date date = new Date(System.currentTimeMillis());
	        calendar.setTime(date);
	        calendar.add(Calendar.YEAR, 20);
	        date = calendar.getTime();
		HashMap dateMap =new HashMap();
		dateMap.put("userId", userId);
		dateMap.put("endDate", date);
		dateMap.put("status", "Y");
		auDao.updateStatus(dateMap);
	}


	@Override
	public ArrayList<HashMap> getFunTreeForNone() {
		
		return auDao.getFunTreeForNone();
	}


	@Override
	public void insertUserRole(List rolelist) {
	 auDao.insertUserRole(rolelist);
	}

	@Override
	public void insertUserFun(List userlist) {
		auDao.insertUserFun(userlist);
		
	}


	@Override
	public boolean isResetPassword(String userId) {
		return auDao.isResetPassword(userId);
	}
	
	/**
	 * 判断密码是否失效
	 * @param userId
	 * @return
	 */
	public boolean isIneffectivePassword(String userId) {
		return auDao.isIneffectivePassword(userId);
	}

	@Override
	public boolean isDefineDefaultRole(String userId) {
		return auDao.isDefineDefaultRole(userId);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public void saveDefaultRole(UserRole userRole) throws Exception{

		auDao.saveDefaultRole(userRole);
	}

	@Override
	public String getDefaultRoleByUserId(String userId) {
		return auDao.getDefaultRoleByUserId(userId);
	}

	/**
	 * 用户修改密码
	 * @param user
	 * @throws Exception
	 */
	public void modifyPassword(User user) throws Exception {
		auDao.modifyPassword(user);
	}
	
	@Override
	public Role getRoleById(String roleId) {
		
		return auDao.getRoleById(roleId);
	}

	@Override
	public List<HashMap> getRolesByUserId(String userId) {
		return auDao.getRolesByUserId(userId);
	}

	@Override
	public FunTreeNode loadMnuForRole(
			HashMap<String, String> params) {
		// TODO Auto-generated method stub
		return auDao.loadMnuForRole(params);
	}


}
