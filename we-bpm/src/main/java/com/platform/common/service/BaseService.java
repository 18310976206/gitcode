package com.platform.common.service;

import java.util.List;
import java.util.Map;

import com.platform.common.dao.BaseDao;
import com.platform.common.page.TabPage;

/**
 * Service 公共接口
 * @param <T>
 * @param <PK>
 */
public interface BaseService<T> {

	public TabPage<T> selectForPage(Map<String, Object> param) throws Exception;
	

	public T selectByPK(Map<String, Object> params) throws Exception;
	
	
	
	public T selectByPropertys(Map<String, Object> params) throws Exception;
	
	
	public void insert(T entity) throws Exception;
	

	public void update(T entity) throws Exception;
	
	
	public void delete(List<T> entitys) throws Exception;
	

	public void delete(T entity) throws Exception;

	public List<T> findAll(Map<String, Object> param) throws Exception;

	void checkDataBeforeDalete(T entity) throws Exception;
	
	public BaseDao<T> getDao() throws Exception;
	
}
