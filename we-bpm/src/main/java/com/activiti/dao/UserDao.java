package com.activiti.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.activiti.bean.Users;

/************************************************/
/*FileName     :    UserDao.java                            
Author        :	   gzl 
Date          :    2020年8月12日 下午3:48:05                                                            
Brief Description: TODO                  */
/************************************************/
/**
 * 参数一 T :当前需要映射的实体
 * 参数二 ID :当前映射的实体中的OID的类型
 *
 */
public interface UserDao extends JpaRepository<Users,Integer>{

//	 @Query("from Users where name = ?")
//	    List<Users> queryByNameUseHQL(String name);
//
//	    @Query(value = "select * from t_user where name=?",nativeQuery = true)
//	    List<Users> queryByNameUseSQL(String name);
//
//	    @Query("update Users set name=? where id=?")
//	    @Modifying  //需要执行一个更新操作
//	    void updateUsersNameById(String name,Integer id);
}

 
 
