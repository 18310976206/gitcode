package com.platform.au.entity;

import java.util.Date;

import com.platform.common.modal.Model;

public class User extends Model{
	// 用户ID
	private String id;
	// 用户名
	private String name ;
	// 员工ID
	private String empId ;
	// 员工编码
	private String empCode ;
	// 员工姓名
	private String empName ;
	// 员工性别
	private String empSex ;
	// 电子邮箱
	private String email ;
	// 手机号码
	private String phoneNo ;
	// 密码
	private String password;
	
	private String oldPassword ; 	// 原密码
	
	private Date lastChgPwDate ; // 最近一次修改日期
	
	private String restPwdFlag ;  // 密码是否被重置
	// 开始日期
	private Date sDate ;
	// 结束日期
	private Date eDate ;

	private String isValidPassword; //用于标示是否对源密码进行校验：N校验，其它不校验

	// 用户类型：S，系统内置用户；U，自定义用户
	private String type ;

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	public String getsId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpSex() {
		return empSex;
	}

	public void setEmpSex(String empSex) {
		this.empSex = empSex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public Date getLastChgPwDate() {
		return lastChgPwDate;
	}

	public void setLastChgPwDate(Date lastChgPwDate) {
		this.lastChgPwDate = lastChgPwDate;
	}

	public String getRestPwdFlag() {
		return restPwdFlag;
	}

	public void setRestPwdFlag(String restPwdFlag) {
		this.restPwdFlag = restPwdFlag;
	}

	public Date getsDate() {
		return sDate;
	}

	public void setsDate(Date sDate) {
		this.sDate = sDate;
	}

	public Date geteDate() {
		return eDate;
	}

	public void seteDate(Date eDate) {
		this.eDate = eDate;
	}

	public String getIsValidPassword() {
		return isValidPassword;
	}

	public void setIsValidPassword(String isValidPassword) {
		this.isValidPassword = isValidPassword;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}


	
}
