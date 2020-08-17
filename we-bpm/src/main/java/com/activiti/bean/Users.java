package com.activiti.bean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/************************************************/
/*FileName     :    Users.java                            
Author        :	   gzl 
Date          :    2020年8月12日 下午3:44:52                                                            
Brief Description: TODO                  */
/************************************************/

@Entity
@Table(name="t_users")
public class Users {

	@Id	//主键id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//主键生成策略
	@Column(name="id")//数据库字段名
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="age")
	private Integer age;
	
	@Column(name="address")
	private String address;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Users{");
		sb.append("id=").append(id);
		sb.append(", name='").append(name).append('\'');
		sb.append(", age=").append(age);
		sb.append(", address='").append(address).append('\'');
		//sb.append(", roles=").append(roles);
		sb.append('}');
		return sb.toString();
	}
}

 
 
