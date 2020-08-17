package com.platform.common.utils.orgSecurity;

import java.util.Map;

import com.platform.common.modal.OrgSec;

public class OrgSecurity {

	public static String getOrgSecurityString(OrgSec orgSec) {
		// TODO Auto-generated method stub
        //do....
		String result = "''";
		return result;
	}
	
	
	private static OrgSec resetParam(OrgSec orgSec){
		//如果需要根据用户ID隔离 do..

		return orgSec;
	}
	
	public static Map<String,Object> getOrgSecStringByMap(Map<String,Object> param){
		OrgSec orgSec = new OrgSec();
		if(param.containsKey("userId")){
			orgSec.setUserId(param.get("userId") + "");
		}
		if(param.containsKey("roleId")){
			orgSec.setRoleId(param.get("roleId") + "");
		}
		if(param.containsKey("funId")){
			orgSec.setFunId(param.get("funId") + "");
		}
		if(param.containsKey("appId")){
			orgSec.setAppId(param.get("appId") + "");
		}
		if(param.containsKey("userIdFlag")){
			orgSec.setUserIdFlag((Boolean)param.get("userIdFlag"));
		}
		if(param.containsKey("funIdFlag")){
			orgSec.setFunIdFlag((Boolean)param.get("funIdFlag"));
		}
		if(param.containsKey("roleIdFlag")){
			orgSec.setRoleIdFlag((Boolean)param.get("roleIdFlag"));
		}
		if(param.containsKey("appIdFlag")){
			orgSec.setAppIdFlag((Boolean)param.get("appIdFlag"));
		}
		if(param.containsKey("selfFlag")){
			orgSec.setSelfFlag((Boolean)param.get("selfFlag"));
		}
		param.put("org_security", OrgSecurity.getOrgSecurityString(orgSec));
		return param;
	}
	
}
