package com.platform.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 
* <b>Description:</b><br> 返回结果
* @author <a href="" target="_blank">gzl</a>
* @version 1.0
* @Note
* <b>ProjectName:</b> we-bpm
* <br><b>PackageName:</b> com.util
* <br><b>ClassName:</b> Result
* <br><b>Date:</b> 2020年8月12日 下午4:24:08
 */
public class Result extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public Result() {
		put("code", 1);
		put("msg", "success");
	}
	
	public static Result fail() {
		return fail(0, "未知异常，请联系管理员");
	}
	
	public static Result fail(String msg) {
		return fail(0, msg);
	}
	
	public static Result fail(int code, String msg) {
		Result r = new Result();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static Result success(String msg) {
		Result r = new Result();
		r.put("msg", msg);
		return r;
	}
	
	public static Result success(Map<String, Object> map) {
		Result r = new Result();
		r.putAll(map);
		return r;
	}
	
	public static Result success() {
		return new Result();
	}

	@Override
	public Result put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
