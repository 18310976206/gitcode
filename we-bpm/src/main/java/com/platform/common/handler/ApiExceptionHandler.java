package com.platform.common.handler;

import java.sql.SQLException;

import org.apache.ibatis.type.TypeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.platform.common.exception.MessageException;
import com.platform.common.page.JsonVo;

@ControllerAdvice
public class ApiExceptionHandler {
	
//	public Logger logger = Logger.getLogger(this.getClass());
	
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public JsonVo<String> badRequest(Exception e){
		JsonVo<String> vo=new JsonVo<String>();
		vo.fail(getExceptionMsg(e));
		//logger.error(e);
		return vo;
	}
	public static String getExceptionMsg(Exception e){
		String msg="操作失败";
		if(e.getCause() instanceof SQLException){
			SQLException se=(SQLException)e.getCause();
			int errorCode=se.getErrorCode();
			return se.getLocalizedMessage();
		}else if(e.getCause() instanceof TypeException){
			return "当前数据类型与数据库类型不一致,操作数据库失败!!";
		}else if(e instanceof MessageException){
			msg=e.getMessage();
		}else {
			return msg+"："+e.getMessage();
		}
		return msg;
	}
}
