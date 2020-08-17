package com.platform.common.modal;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.platform.common.utils.enumutil.EnumManager;
/**
 * 
 * @author gzl
 *
 */
public abstract class Model {
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	protected Date lastUpdateDate ;//最后更新时间
	protected Object lastUpdatedBy;//最后更新人
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	protected Date creationDate;//创建时间
	protected Object createdBy;//创建人
	protected String lastUpdateLogin;//最后登陆人
	protected String riId;//实例id
	protected String busStatus;//审批状态
	protected String attr1;//扩展字段1
	protected String attr2;//扩展字段2
	protected String attr3;//扩展字段3
	protected String attr4;//扩展字段4
	protected String attr5;//扩展字段5
	protected String attr6;//扩展字段6
	protected String attr7;//扩展字段7
	protected String attr8;//扩展字段8
	protected String attr9;//扩展字段9
	protected String attr10;//扩展字段10

	public abstract void setId(String id);
	
	@JsonIgnore
	public String getId(){
		return null;
	};

	public enum WorkflowStatus{
		NULL(null,"未上报"),
		EMPTY("","未上报"),
		A("A","未上报"),
		C("C","<font color=green>审批中<font>"),
		E("E","<font color=green>审批通过<font>"),
		D("D","<font color=red>驳回<font>"),
		R("R","撤回");
		private String text;
		private String value;
		private WorkflowStatus(String value,String text) {
			this.value=value;
			this.text = text;
		}
		public String getValue(){
			return this.value;
		}
		public String getText() {
			return text;
		}
	}

	public void setWhoForInsert(String userId){
		this.createdBy = userId;
		this.creationDate = new Date();
		this.lastUpdateLogin = userId;
	}
	
	public void setWhoForUpdate(String userId){
		this.lastUpdatedBy = userId;
		this.lastUpdateDate = new Date();
		this.lastUpdateLogin = userId;
	}

	public String getsBusinessStatus() {
		if(StringUtils.isBlank(this.busStatus)){
			this.busStatus=WorkflowStatus.A.getValue();
		}
		return busStatus;
	}

	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",locale = "zh" , timezone="GMT+8")
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",locale = "zh" , timezone="GMT+8")
	public void setLastUpdateDate(Date last_update_date) {
		this.lastUpdateDate = last_update_date;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",locale = "zh" , timezone="GMT+8")
	public Date getCreationDate() {
		return creationDate;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",locale = "zh" , timezone="GMT+8")
	public void setCreationDate(Date creation_date) {
		this.creationDate = creation_date;
	}


	public String getStatusT(){
		return EnumManager.getText(WorkflowStatus.class, this.busStatus);
	}

	public Object getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Object sLastUpdatedBy) {
		this.lastUpdatedBy = sLastUpdatedBy;
	}

	public Object getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Object createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setLastUpdateLogin(String lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	public String getRiId() {
		return riId;
	}

	public void setRiId(String riId) {
		this.riId = riId;
	}

	public String getBusStatus() {
		return busStatus;
	}

	public void setBusStatus(String busStatus) {
		this.busStatus = busStatus;
	}

	public String getAttr1() {
		return attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}

	public String getAttr2() {
		return attr2;
	}

	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}

	public String getAttr3() {
		return attr3;
	}

	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}

	public String getAttr4() {
		return attr4;
	}

	public void setAttr4(String attr4) {
		this.attr4 = attr4;
	}

	public String getAttr5() {
		return attr5;
	}

	public void setAttr5(String attr5) {
		this.attr5 = attr5;
	}

	public String getAttr6() {
		return attr6;
	}

	public void setAttr6(String attr6) {
		this.attr6 = attr6;
	}

	public String getAttr7() {
		return attr7;
	}

	public void setAttr7(String attr7) {
		this.attr7 = attr7;
	}

	public String getAttr8() {
		return attr8;
	}

	public void setAttr8(String attr8) {
		this.attr8 = attr8;
	}

	public String getAttr9() {
		return attr9;
	}

	public void setAttr9(String attr9) {
		this.attr9 = attr9;
	}

	public String getAttr10() {
		return attr10;
	}

	public void setAttr10(String attr10) {
		this.attr10 = attr10;
	}

	
	
	
}
