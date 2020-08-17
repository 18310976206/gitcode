package com.platform.common.utils.listUtil;

import java.util.ArrayList;
import java.util.List;

public class ListsGet {
	
	/**
	 * list分割成长度为n的多个数组
	 * @param list 初始数组
	 * @param n 分割后数据的长度
	 * @return
	 */
	public static<T> List<List<T>> getLists(List<T> list, int n) {
		//根据参数n，将list分割成长度为n的多个数组返回
	    if (null == list || list.size() == 0 || n <= 0) return null;
	    
	    List<List<T>> result = new ArrayList<List<T>>();
	
	    int size = list.size();
	    int num = (list.size() / n) + 1;
	    for (int i = 0; i < num; i++) {
	        List<T> list_map = new ArrayList<T>();
	        for (int j = i * n; j < (i + 1) * n; j++) {
	            if (j < size) {
	            	list_map.add(list.get(j));
	            }
	        }
	        result.add(list_map);
	    }
	    return result;
	}
}
