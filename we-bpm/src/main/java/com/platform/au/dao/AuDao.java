package com.platform.au.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.platform.au.entity.FunTreeNode;
import com.platform.au.entity.LoginLog;
import com.platform.au.entity.Role;
import com.platform.au.entity.User;
import com.platform.au.entity.UserRole;


public interface AuDao {
	 public List<HashMap> getParamList(String paramCode, String targetId);
	    public List<HashMap> getValueSetDtlList(String valueSetCode);

	    public List<HashMap> getSecOrgList(String targetId);

	    public List<HashMap> getUserList();

	    public List<HashMap> getEmpList();

	    public List<HashMap> getUserFavoriteFuntions(HashMap params);
	    
	    public void insertLoginLog(LoginLog log);

	    public void updateLogout(LoginLog logout);
	    
	    public List<HashMap> getUserFunDataByUserId(HashMap<String, String> map);
	    
	    public List<HashMap> getUserUnFunDataByUserId(HashMap<String, String> map);

		public List<HashMap> empCodeValidation(String empCode);

		public List<HashMap> userNameValidation(String userName);
		
		public void updateStatus(HashMap dateMap);
		

	    public List<HashMap> getPassWordListByUserName(String userName);
	    
	    public void updatePassWord(HashMap map);

		public ArrayList<HashMap> getFunTreeForNone();

		public void insertUserRole(List rolelist);

		public void insertUserFun(List userlist);

		/**
		 * 
		 * :判断是否重置密码
		 */
		public boolean isResetPassword(String userId);
		
		/**
		 * 判断密码是否失效
		 */
		public boolean isIneffectivePassword(String userId);	
		
		/**
		 * 
		 * :判断是否定义了默认职责
		 */
		public boolean isDefineDefaultRole(String userId);
		
		/**
		 * 
		 * :保存用户默认职责
		 */
		public void saveDefaultRole(UserRole userRole) throws Exception;
		
		/**
		 * 
		 * :取得用户对应的默认角色
		 */
		public String getDefaultRoleByUserId(String userId);
		
	

		
		/**
		 * 
		 * :用户更改密码
		 */
		public void modifyPassword(User user) throws Exception;
		
		/**
		 * 根据roleid取得role
		 */
		public Role getRoleById(String roleId);
		
		/**
		 * : 根据用户id获取角色List
		 */
		public List<HashMap> getRolesByUserId(String userId);
		
		public FunTreeNode loadMnuForRole(HashMap<String, String> params);
}
