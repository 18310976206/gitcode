package com.platform.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.platform.common.modal.Model;
import com.platform.common.page.TabPage;
import com.platform.common.service.BaseService;

public abstract class BaseServiceImpl<T extends Model> implements BaseService<T>{
	
	
	public TabPage<T> selectForPage(Map<String, Object> param) throws Exception{
		TabPage<T> tabPage=new TabPage<T>();
		int total=getDao().selectCount(param);
		if(total>0){
			
			if("statusT".equals(param.get("orderName"))){
		    	param.put("orderName","status");
		    }
			
			//param.put("databaseType", PlatformUtil.getDbType());
			
			int offset = Integer.parseInt(String.valueOf(param.get("offset")));
			int limit = Integer.parseInt(String.valueOf(param.get("limit")));
			
			//如果是最后一页则显示上一页的内容，否则如果是选择的记录大于总记录数，则从第0条记录开始显示
			if(total!=0&&total==offset){
				offset = total-limit;
				param.put("offset", offset);
			}else if(total!=0&&offset>total){
				param.put("offset", 0);
			}
			List<T> list=getDao().selectForPage(param);
			tabPage.setRows(list);
		}else{
			tabPage.setRows(new ArrayList<T>());
		}
		tabPage.setTotal(total);
		return tabPage;
	}
	
	
	
	public T selectByPK(Map<String, Object> params) throws Exception{
		return  getDao().selectByPK(params);
	}

	@Override
	public T selectByPropertys(Map<String, Object> params) throws Exception{
		return getDao().selectByPropertys(params);
	}
	
	@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED,rollbackFor={Exception.class})
	public void insert(T entity) throws Exception{
		 getDao().insert(entity);
	}

	
	@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED,rollbackFor={Exception.class})
	public void update(T entity) throws Exception{
		 getDao().update(entity);
	}
	
	
	@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED,rollbackFor={Exception.class})
	public void delete(List<T> entitys) throws Exception {
		getDao().delete(entitys);
	}
	
	
	@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED,rollbackFor={Exception.class})
	public void delete(T entity) throws Exception {
		getDao().delete(entity);
	}

	
	public List<T> findAll(Map<String, Object> param) throws Exception{
		return  getDao().findAll(param);
	}
	
	@Override
	public void checkDataBeforeDalete(T entity) throws Exception{}
}
