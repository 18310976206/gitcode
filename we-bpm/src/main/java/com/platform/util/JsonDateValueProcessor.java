package com.platform.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * 使用json-lib将对象中的Date转化为指定日期格式的json串
 * 
 */
public class JsonDateValueProcessor implements JsonValueProcessor {
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";  
    private DateFormat dateFormat;  
  
    /**   
     * 构造方法.   
     *   
     * @param datePattern 日期格式   
     */  
    public JsonDateValueProcessor(String datePattern) {  
        try {  
            dateFormat = new SimpleDateFormat(datePattern);  
        } catch (Exception ex) {  
            dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);  
        }  
    }  
  
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {  
        return process(value);  
    }  
  
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {  
        return process(value);  
    }  
  
    private Object process(Object value) {  
		try {
			if (value != null && value instanceof Date) {
				return dateFormat.format((Date) value);
			}
			return value == null ? "" : value.toString();
		} catch (Exception e) {
			return value;
		}    	
    }  
}
