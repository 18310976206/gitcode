package com.platform.au.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.platform.au.entity.FunTreeNode;
import com.platform.au.entity.Role;
import com.platform.au.entity.User;
import com.platform.au.entity.UserRole;


public interface AuService {
	public String getValueSetDtlList(String valueSetCode);

	public String getParamList(String paramCode, String targetId);

	public String getSecOrgList(String targetId);

	public String getUserList();

	public String getEmpList();

	
	public List<HashMap> getUserFavoriteFuntions(HashMap params);
	
	/**
	 *获取用户功能授权的树形结构 
	 */
	public List<HashMap> getUserFunTreeByUserId(HashMap<String, String> map);
	
	
    public List<HashMap> getPassWordListByUserName(String userName);
    
    public void updatePassWord(String passWord,String userId);
    

	public String empCodeValidation(String empCode);

	public String userNameValidation(String userName);

	public void updateStatus(String userId);
	/**
	 *获取功能授权的树形结构 
	 */
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
	public boolean isIneffectivePassword(String userId) ;
	
	/**
	 * 
	 * 判断是否定义了默认职责
	 */
	public boolean isDefineDefaultRole(String userId);
	
	/**
	 * 
	 * :判断是否定义了默认职责
	 */
	public void saveDefaultRole(UserRole userRole) throws Exception;
	
	/**
	 * 
	 * :取得默认角色
	 */
	public String getDefaultRoleByUserId(String userId);
	
	/**
	 * 
	 * :判断是否定义了默认职责
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
