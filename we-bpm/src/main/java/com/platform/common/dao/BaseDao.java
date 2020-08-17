package com.platform.common.dao;

import java.util.List;
import java.util.Map;

public interface BaseDao<T> {

	public List<T> selectForPage(Map<String, Object> param) throws Exception;

	public Integer selectCount(Map<String, Object> param) throws Exception;
	

	public T selectByPK(Map<String, Object> params) throws Exception;
	
	public T selectByPropertys(Map<String, Object> params) throws Exception;
	
	public void checkData(T entity) throws Exception;

	public void checkDataBeforeDalete(T entity) throws Exception;
	
	public void insert(T entity) throws Exception;
	
	public void update(T entity) throws Exception;
	
	public void delete(T entity) throws Exception;
	
	public void deleteByParam(Map<String, Object> param) throws Exception;
	
	
	public void delete(List<T> entity) throws Exception;
	
	public List<T> findAll(Map<String, Object> param) throws Exception;

	void checkDataBeforeDalete(Map<String, Object> params) throws Exception;
	
}
