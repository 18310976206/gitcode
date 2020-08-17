package com.platform.au.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.platform.au.dao.AuDao;
import com.platform.au.entity.FunTreeNode;
import com.platform.au.entity.LoginLog;
import com.platform.au.entity.Role;
import com.platform.au.entity.User;
import com.platform.au.entity.UserRole;


@Repository("auDao")
public class AuDaoImpl implements AuDao {

    @Override
    public List<HashMap> getValueSetDtlList(String valueSetCode) {
        return null;
    }

    @Override
    public List<HashMap> getParamList(String paramCode, String targetId) {
    	return null;
    }

    @Override
    public List<HashMap> getSecOrgList(String targetId) {
    	return null;
    }

    @Override
    public List<HashMap> getUserList() {
    	return null;
    }


    @Override
    public List<HashMap> getEmpList() {
    	return null;
    }


    @Override
    public List<HashMap> getUserFavoriteFuntions(HashMap params) {
    	return null;
    }


    @Override
    public void insertLoginLog(LoginLog log) {
    }

    @Override
    public void updateLogout(LoginLog logout) {
    }


	@Override
	public List<HashMap> getUserFunDataByUserId(HashMap<String, String> map) {
    	return null;
	}
    
    @Override
    public List<HashMap> getPassWordListByUserName(String userName){
    	return null;
    }
    @Override
    public void updatePassWord(HashMap map){
    	
    }

	@Override
	public List<HashMap> empCodeValidation(String empCode) {
    	return null;
	}

	@Override
	public List<HashMap> userNameValidation(String userName) {
    	return null;
		}

	@Override
	public void updateStatus(HashMap dateMap) {
	}


	@Override
	public ArrayList<HashMap> getFunTreeForNone() {
    	return null;
	}


	@Override
	public void insertUserRole(List rolelist) {
	}

	@Override
	public void insertUserFun(List userlist) {
	}

	@Override
	public List<HashMap> getUserUnFunDataByUserId(HashMap<String, String> map) {
    	return null;
	}

    @Override
    public boolean isResetPassword(String userId) {
    	return false;
    }

    /**
     * 判断密码是否失效
     */
    public boolean isIneffectivePassword(String userId) {
    	return false;
    }

    @Override
    public boolean isDefineDefaultRole(String userId) {
    	return false;
    }

    @Override
    public void saveDefaultRole(UserRole userRole) throws Exception {
    }

    @Override
    public String getDefaultRoleByUserId(String userId) {
    	return null;
    }

    /**
     * 用户修改密码
     */
    public void modifyPassword(User user) throws Exception {
    }

    /**
     * 判断用户设置的密码是否合法
     */
    private void isValidPassword(User user) throws Exception {
    }

    @Override
    public Role getRoleById(String roleId) {
    	return null;
    }

    @Override
    public List<HashMap> getRolesByUserId(String userId) {
    	return null;
    }

    /**
     * 根据角色加载菜单
     */
	@Override
	public FunTreeNode loadMnuForRole(
			HashMap<String, String> params) {
    	return null;
	}
	
	private ArrayList<FunTreeNode> parseMnuTree(ArrayList<FunTreeNode> treeNodes, String parentId){

    	return null;
	}






}
