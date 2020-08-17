package com.platform.common.page;

import java.util.List;

/**
 * 分页结果
 * @param <T>
 */
public class TabPage<T> {
	protected int total;
	protected List<T> rows;	
	protected String msg;
	
	public static final int DEFAULT_LIMIT = 20;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getRecordsTotal(){
		return this.total;
	}
	
	public int getRecordsFiltered(){
		return this.total;
	}
	public List<T> getData(){
		return rows;
	}
	
	public String getError(){
		return msg;
	}
}
