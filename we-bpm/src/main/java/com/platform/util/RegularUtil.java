package com.platform.util;

import java.util.regex.Pattern;

public class RegularUtil {

	/**
	 * 判断输入的表达式是否为非负整数(正整数   + 0)
	 * @param exp
	 * @return
	 */
	public static boolean isInteger(String input){
//		String regex = "^[0-9]*[1-9][0-9]*$" ;	// 正整数
		String regex = "^\\d+$" ;		// 非负整数(正整数   + 0)
		Pattern p = Pattern.compile(regex) ;
		return p.matcher(input).find() ;
	}
	
	/**
	 * 判断表达式串中是否包含字母和数字
	 * @param exp
	 * @return
	 */
	public static boolean isIncludeCharAndNumberic(String input){
		boolean flag = true ;
		
		// 判断是否包含字母
		String reg1 = "[a-zA-Z]" ;
		Pattern p1 = Pattern.compile(reg1) ;
		if(!p1.matcher(input).find()){	
			return false ; 
		}
		// 判断是否包含数字
		String reg2 = "\\d" ;
		Pattern p2 = Pattern.compile(reg2) ;
		if(!p2.matcher(input).find()){
			return false ; 
		}
		return flag ; 
	}
	
	/**
	 * 判断字符串是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumberic(String input) {
		try {
			String regex = "^[0-9]\\d*(\\.)?\\d*[0-9]$" ;
			
			Pattern p = Pattern.compile(regex); 
			
			return p.matcher(input).find() ;
			
		} catch (Exception e) {
			return false ;
		}
	}
}
