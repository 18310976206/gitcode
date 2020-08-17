package com.platform.common.utils.enumutil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


public class EnumManager {
	
	
	
	public static <T> List<Map<String, String>> getTypes(Class<T> c){
		return getTypes(c,null, null);
	}
	
	
	public static <T> List<Map<String, String>> getTypes(Class<T> c,String textField,String valueField){
		List<Map<String, String>> es=new ArrayList<Map<String,String>>();
		Method valueMethod=null;
		try {
			
			if(StringUtils.isBlank(textField)){textField="text";}
			if(StringUtils.isBlank(valueField)){valueField="id";}
			
			valueMethod =c.getMethod("getValue");
			Method textMethod=c.getMethod("getText");
			Map<String, String> value=null;
			
			for(T e:c.getEnumConstants()){
				value=new HashMap<String,String>();
				value.put(valueField,String.valueOf(valueMethod.invoke(e)));
				value.put(textField,String.valueOf(textMethod.invoke(e)));
				es.add(value);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return es;
	}
	
	 public static <T> Map<String, String> parseEumn(Class<T> c){  
		 	Map<String, String> map = new LinkedHashMap<String, String>() ;  
		 	Method valueMethod=null;
		 	try {
	        if(c.isEnum()){  
	        	valueMethod =c.getMethod("getValue");
				Method textMethod=c.getMethod("getText");
				for(T e:c.getEnumConstants()){
					map.put(String.valueOf(valueMethod.invoke(e)),String.valueOf(textMethod.invoke(e)));
				}
	        }  
		 	} catch (Exception e) {
				e.printStackTrace();
			} 
	        return map ;  
	}
	 
    public static String getText(Class<?> ref , String value){  
        return parseEumn(ref).get(value) ;   
    }  
	 
}
