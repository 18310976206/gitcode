package com.platform.common.page;

import java.io.Serializable;
/**
  * @Description: 
  * @param <T>  flag 0 正确   1 错误   msg
 */
public class JsonVo<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private String flag;
	private String msg;
	private T obj;

	/**
	  * @Description: 设置为成功状态
	 */
	public void success(String msg){
		this.flag = "0";
		this.msg = msg;
	}
	/**
	  * @Description: 设置为失败状态
	 */
	public void fail(String msg){
		this.flag = "1";
		this.msg = msg;
	}
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

}
