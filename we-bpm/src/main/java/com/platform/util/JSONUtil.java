package com.platform.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.xml.XMLSerializer;

public class JSONUtil {
	/**
	 * JAVA对象动态转换为JSON字符串
	 * @param obj
	 * @return
	 */
	public static String Encode(Object obj) {
		if(obj == null || obj.toString().equals("null")) return null;
		if(obj != null && obj.getClass() == String.class){
			return obj.toString();
		}
		JSONSerializer serializer = new JSONSerializer();
		serializer.transform(new DateTransformer("yyyy-MM-dd'T'HH:mm:ss"), Date.class);
		serializer.transform(new DateTransformer("yyyy-MM-dd'T'HH:mm:ss"), Timestamp.class);
		String json = serializer.deepSerialize(obj);
		
		//System.out.println("JSON数据：\n"+json+"\n\n");
		
		return json;
	}
	
	/**
	 * JSON字符串动态转换为JAVA对象
	 * @param json
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object Decode(String json) {
		if (StringUtil.isNullOrEmpty(json)) return "";
		JSONDeserializer deserializer = new JSONDeserializer();
		//deserializer.use(String.class, new DateTransformer("yyyy-MM-dd'T'HH:mm:ss"));		
		Object obj = deserializer.deserialize(json);
		if(obj != null && obj.getClass() == String.class){
			return Decode(obj.toString());
		}		
		return obj;
	}
	
	public static String xml2json(String xmlString){    
		XMLSerializer xmlSerializer = new XMLSerializer();  
		net.sf.json.JSON json = xmlSerializer.read(replaceTRN(xmlString));    
		return json.toString();    
	}   
	
	/**
	 * 返回符合ExtJS store格式的JSON字符串
	 * @param <T>
	 * @param json
	 * @return
	 */
	public static <T> String beansTojsonStore(List<T> beans,String dateFormat){
		// 日期转换
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(dateFormat));
		
		String json = beanListToJson(beans,cfg) ;
		return json="{dataList:"+json+",totalProperty:"+beans.size()+"}";
	}
	
	/**
	 * 返回符合ExtJS store格式的JSON字符串(执行日期转换处理)
	 * 
	 * @param beans
	 * @param _nory_changes
	 * @param nory
	 * @return
	 */
	public static <T> String beansTojsonStore(List<T> beans, String[] _nory_changes, boolean nory,String dateFormat){
		String json = beanListToJson(beans, _nory_changes, nory, dateFormat) ;
		return json="{dataList:"+json+",totalProperty:"+beans.size()+"}";
	}
	
	/**
	 * 返回符合ExtJS store格式的JSON字符串(不执行日期转换处理)
	 * 
	 * @param beans
	 * @param _nory_changes
	 * @param nory
	 * @return
	 */
	public static <T> String beansTojsonStore(List<T> beans, String[] _nory_changes, boolean nory){
		String json = beanListToJson(beans, _nory_changes, nory, null) ;
		return json="{dataList:"+json+",totalProperty:"+beans.size()+"}";
	}
	
	/**
	 * 返回符合ExtJS store格式的JSON字符串
	 * @param list
	 * @return
	 */
	public static <T> String listToJsonStore(List<T> list){
		String json = listToJson(list) ;
		return json="{dataList:"+json+",totalProperty:"+list.size()+"}";
	}
	
	
    /**
     * 从一个JSON 对象字符格式中得到一个java对象  
     * @param bean
     * @return
     */
	public static String beanToJson(Object bean,JsonConfig cfg){
		JSONObject json = null ;
		if(cfg == null){
			json = JSONObject.fromObject(bean);
		}else{
			json = JSONObject.fromObject(bean,cfg);
		}
		return json.toString() ;
	}	
	/**
	 * 对象转map
	 * @Title: beanToMap
	 * @Description: 
	 * @Author gzl
	 * @param bean
	 * @param cfg
	 * @return
	 */
  
   @SuppressWarnings("unchecked")
   public static Map<String,Object> beanToMap(Object bean,JsonConfig cfg) {
		 JSONObject jsonObject = null ;
			if(cfg == null){
				jsonObject = JSONObject.fromObject(bean);
			}else{
				jsonObject = JSONObject.fromObject(bean,cfg);
			}
	        
	        Iterator<String> keyIter = jsonObject.keys();
	       
	        Map<String,Object> map = new HashMap<>();
	        while (keyIter.hasNext()) {
	        	String key = (String) keyIter.next();
	        	Object value = jsonObject.get(key).toString();
	            map.put(key, value);
	        }
	        
	        return map;
	    } 
	
	/**  
     * 将java对象转换成json字符串  
     *
     * @param bean  
     * @return  
     */
    public static String beanToJson(Object bean, String[] _nory_changes, boolean nory, String dateFormat) {
      JSONObject json = null;
      //转换_nory_changes里的属性
      if(nory){ 
    		Field[] fields = bean.getClass().getDeclaredFields();
    		String str = "";
    		for(Field field : fields){
    			str+=(":"+field.getName());
    		}
    		fields = bean.getClass().getSuperclass().getDeclaredFields();
    		for(Field field : fields){
    			str+=(":"+field.getName());
    		}
    		str+=":";
    		for(String s : _nory_changes){
    			str = str.replace(":"+s+":", ":");
    		}
    		json = JSONObject.fromObject(bean,configJson(str.split(":"),dateFormat));
    		 
    	}else{//转换除了_nory_changes里的属性
    		json = JSONObject.fromObject(bean,configJson(_nory_changes,dateFormat));
    	}
    	return json.toString();      
    }	
    
    private static JsonConfig configJson(String[] excludes, String dateFormat) {   
        JsonConfig jsonConfig = new JsonConfig();   
        jsonConfig.setExcludes(excludes);   
        jsonConfig.setIgnoreDefaultExcludes(false); 
        // 日期转换
        if(dateFormat != null){
        	jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(dateFormat));
        }
        return jsonConfig;   
    }  
    
    
    /**
     * 将java对象List集合转换成json字符串  
     * @param <T>
     * @param beans
     * @return
     */
    public static <T> String beanListToJson(List<T> beans,JsonConfig cfg) {
    	int size = beans.size();
    	
    	// 拼接JSON字符串
        StringBuffer result = new StringBuffer();
        result.append("[");
        if(beans != null && size >0){
            for(int i=0; i<size; i++){
            	result.append(beanToJson(beans.get(i),cfg)+((i<size-1) ? ",":""));
            }
        }
        result.append("]");
        
        String json = result.toString();
        
        return json ;
    }
    
    /**
     * 将java对象List集合转换成json字符串  
     * @param <T>
     * @param beans
     * @param _no_changes
     * @return
     */
    public static <T> String beanListToJson(List<T> beans, String[] _nory_changes, boolean nory, String dateFormat) {
    	int size = beans.size();
    	
    	// 拼接JSON字符串
        StringBuffer result = new StringBuffer();
        result.append("[");
        if(beans != null && size >0){
            for(int i=0; i<size; i++){
                try{
                	result.append(beanToJson(beans.get(i),_nory_changes,nory, dateFormat));
                    if(i<size-1){
                    	result.append(",");
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }       	
        }
        result.append("]");
        
        String json = result.toString();
        
        return json;
    }
    
    /**
     * 将List数组专为JSON对象
     * @param list
     * @return
     */
    public static <T> String listToJson(List<T> list) {
        JSONArray jsonArray = JSONArray.fromObject(list);
        return jsonArray.toString();
    }    
    
    /**
     * map集合转换成json格式数据
     * @param map
     * @return
     */
	@SuppressWarnings("unchecked")
	public static <T> String mapToJson(Map<String, ?> map,JsonConfig cfg) {
		String json = "{";

		Set<String> set = map.keySet();
		Iterator<?> it = set.iterator() ;
		
		while(it.hasNext()){
			String s = (String) it.next();
			if(map.get(s) == null){
				
			}else if(map.get(s) instanceof List<?>){
				json += (s + ":" + beanListToJson((List<T>) map.get(s),cfg));

			}else{
				JSONObject str = JSONObject.fromObject(map);
				json += (s + ":" + str.toString());
			}			
			json += ",";
		}
		json += "}";

		return json;
	}  
    
    /**
     * map集合转换成json格式数据
     * @param <T>
     * @param <T>
     * @param map
     * @return
     */
	@SuppressWarnings("unchecked")
	public static <T> String mapToJson(Map<String, ?> map, String[] _nory_changes, boolean nory, String dateFormat) {
		String json = "{";

		Set<String> key = map.keySet();
		for (Iterator<?> it = key.iterator(); it.hasNext();) {
			String s = (String) it.next();
			if (map.get(s) == null) {

			} else if (map.get(s) instanceof List<?>) {
				json += (s + ":" + beanListToJson((List<T>) map.get(s), _nory_changes, nory, dateFormat));

			} else {
				JSONObject jsonStr = JSONObject.fromObject(map);
				json += (s + ":" + jsonStr.toString());
				;
			}

			if (it.hasNext()) {
				json += ",";
			}
		}

		json += "}";

		return json;
	}
     

	
	/**
	 * 从一个JSON 对象字符格式中得到一个java对象  
	 * @param jsonString
	 * @param beanCalss
	 * @return
	 */
    @SuppressWarnings("unchecked")
	public static <T> T jsonToBean(String jsonString, Class<T> beanCalss) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        T bean = (T) JSONObject.toBean(jsonObject, beanCalss);
        return bean;
    }    
    
    /**  
     * 从json HASH表达式中获取一个map，改map支持嵌套功能  
     *
     * @param jsonString  
     * @return  
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map jsonToMap(String jsonString) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        
        Iterator keyIter = jsonObject.keys();
       
        Map map = new HashMap();
        while (keyIter.hasNext()) {
        	String key = (String) keyIter.next();
        	Object value = jsonObject.get(key).toString();
            map.put(key, value);
        }
        
        return map;
    }  
    /**
     * jsonString to jsonObject
     * @author     gzl
     * @param jsonString
     * @return
     * @exception
     */
    public static JSONObject jsonToObject(String jsonString) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        
        return jsonObject;
    }  
    
    /**  
     * 从json数组中得到相应java数组  
     *
     * @param jsonString  
     * @return  
     */
    public static Object[] jsonToObjectArray(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        return jsonArray.toArray();
    }
    
    /**  
     * 从json对象集合表达式中得到一个java对象列表  
     *
     * @param jsonString  
     * @param beanClass  
     * @return  
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> jsonToBeanList(String jsonString, Class<T> beanClass) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        JSONObject jsonObject;
        T bean;
        
        int size = jsonArray.size();
        
        List<T> list = new ArrayList<T>(size);
 
        for(int i = 0; i < size; i++){
            jsonObject = jsonArray.getJSONObject(i);
            bean = (T) JSONObject.toBean(jsonObject, beanClass);
            list.add(bean);
        }
        
        return list;
    }
 
    /**  
     * 从json数组中解析出java字符串数组  
     *
     * @param jsonString  
     * @return  
     */
    public static String[] jsonToStringArray(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        String[] stringArray = new String[jsonArray.size()];
        int size = jsonArray.size();
 
        for(int i=0; i<size; i++) {
            stringArray[i] = jsonArray.getString(i);
        }
        return stringArray;
    }
 
    /**  
     * 从json数组中解析出javaLong型对象数组  
     *
     * @param jsonString  
     * @return  
     */
    public static Long[] jsonToLongArray(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        int size = jsonArray.size();
        Long[] longArray = new Long[size];
        for (int i = 0; i < size; i++) {
            longArray[i] = jsonArray.getLong(i);
        }
        return longArray;
         
    }
 
    /**  
     * 从json数组中解析出java Integer型对象数组  
     *
     * @param jsonString  
     * @return  
     */
    public static Integer[] jsonToIntegerArray(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        
        int size = jsonArray.size();
        
        Integer[] integerArray = new Integer[size];
        
        for(int i=0; i<size; i++) {
            integerArray[i] = jsonArray.getInt(i);
        }
        return integerArray;
    }
 
    /**  
     * 从json数组中解析出java Double型对象数组  
     *
     * @param jsonString  
     * @return  
     */
    public static Double[] jsonToDoubleArray(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        
        int size = jsonArray.size();
        
        Double[] doubleArray = new Double[size];
         
        for (int i=0; i<size; i++) {
            doubleArray[i] = jsonArray.getDouble(i);
        }
        return doubleArray;
    }
    
    /**
     * parseArrayListDateForTree: 转化成ExtJs可识别的树形数据
     *
     * @param list 
     * @param parentId 父id
     * @return
     * @author asus
     */
    public static String parseArrayListDateForTree(ArrayList list, String parentId, String isCheckboxTree){
		String treeDate = "[";
		
		for(int i=0; i<list.size(); i++){
			HashMap map = (HashMap) list.get(i);
			if(parentId.equals(map.get("PARENT_ID"))){
				treeDate = treeDate + "{";
			
				Iterator iterator = map.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry entry = (Entry) iterator.next(); 
					String key = entry.getKey().toString().toLowerCase();
					String val = "";
					if(entry.getValue()!=null){
						val = entry.getValue().toString();
					}
					treeDate = treeDate + key + ":'" + val + "',";
				}
				
				String childrenDate = parseArrayListDateForTree(list,map.get("ID").toString(),isCheckboxTree);
				
				if("Y".equals(isCheckboxTree)){
					if("[]".equals(childrenDate)){
						if(map.get("NODE_TYPE")!=null){
							if("M".equals(map.get("NODE_TYPE").toString())){
								treeDate = treeDate + "leaf:false,checked:false,children:[]";
							}else{
								treeDate = treeDate + "leaf:true,checked:false";
							}
						}else{
							treeDate = treeDate + "leaf:true,checked:false";
						}
					}else{
						treeDate = treeDate + "leaf:false,checked:false,children:" + childrenDate;
					}
				}else{
					if("[]".equals(childrenDate)){
						if(map.get("NODE_TYPE")!=null){
							if("M".equals(map.get("NODE_TYPE").toString())){
								treeDate = treeDate + "leaf:false,children:[]";
							}else{
								treeDate = treeDate + "leaf:true";
							}
						}else{
							treeDate = treeDate + "leaf:true";
						}
					}else{
						treeDate = treeDate + "leaf:false,children:" + childrenDate;
					}
				}
				treeDate = treeDate + "},";
			}
		}
		
		if(treeDate.lastIndexOf(",") == treeDate.length()-1){
			treeDate = treeDate.substring(0, treeDate.length()-1);
		}
		
		treeDate = treeDate + "]";
		
		return treeDate;
	}
    /**
     * parseArrayListDateForTree: 转化成ExtJs可识别的树形数据(带图标)
     *
     * @param list 
     * @param parentId 父id
     * @return
     * @author 
     */
    public static String parseArrayListDateForImgTree(ArrayList list, String parentId, String isCheckboxTree,String iconPath){
		String treeDate = "[";
		
		for(int i=0; i<list.size(); i++){
			HashMap map = (HashMap) list.get(i);
			if(parentId.equals(map.get("PARENT_ID"))){
				treeDate = treeDate + "{";
			
				Iterator iterator = map.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry entry = (Entry) iterator.next(); 
					String key = entry.getKey().toString().toLowerCase();
					String val = "";
					if(entry.getValue()!=null){
						if(entry.getValue() instanceof BigDecimal){
							BigDecimal bd = (BigDecimal) entry.getValue();
							bd.setScale(0, BigDecimal.ROUND_HALF_UP);
							val = bd.toString();
						}else{
							val = entry.getValue().toString();
						}
						
					}
					treeDate = treeDate + key + ":'" + val + "',";
				}
				
				String childrenDate = parseArrayListDateForImgTree(list,map.get("ID").toString(),isCheckboxTree,iconPath);
				
				if("Y".equals(isCheckboxTree)){
					if("[]".equals(childrenDate)){
						if(map.get("NODE_TYPE")!=null){
							if("M".equals(map.get("NODE_TYPE").toString())){
								treeDate = treeDate + "leaf:false,icon:\'tlist_icon\',checked:false,children:[]";
							}else{
								treeDate = treeDate + "leaf:true,checked:false";
							}
						}else{
							treeDate = treeDate + "leaf:true,checked:false";
						}
					}else{
						treeDate = treeDate + "leaf:false,checked:false,children:" + childrenDate;
					}
				}else{
					if("[]".equals(childrenDate)){
						if(map.get("NODE_TYPE")!=null){
							if("M".equals(map.get("NODE_TYPE").toString()) && "-1".equals(map.get("PARENT_ID"))){
								treeDate = treeDate + "leaf:false,iconCls:'folder_icon',children:[]";
							}else if("M".equals(map.get("NODE_TYPE").toString()) && !"-1".equals(map.get("PARENT_ID"))){
								treeDate = treeDate + "leaf:false,icon:'"+iconPath+"',children:[]";	
							}
							else{
								treeDate = treeDate + "leaf:true";
							}
						}else{
							treeDate = treeDate + "leaf:true";
						}
					}else{
						if("-1".equals(map.get("PARENT_ID"))){
							treeDate = treeDate + "leaf:false,iconCls:'folder_icon',children:"+childrenDate;	
						}else{
							treeDate = treeDate + "leaf:false,icon:'"+iconPath+"',children:" + childrenDate;	
						}
						
					}
				}
				treeDate = treeDate + "},";
			}
		}
		
		if(treeDate.lastIndexOf(",") == treeDate.length()-1){
			treeDate = treeDate.substring(0, treeDate.length()-1);
		}
		
		treeDate = treeDate + "]";
		
		return treeDate;
	}
	
	/*private String getChildrenDate(ArrayList list, String parentId){
		String result = "[";
		
		for(int i=0; i<list.size(); i++){
			HashMap map = (HashMap) list.get(i);
			if(parentId.equals(map.get("PARENT_ID"))){
				result = result + "{";
				
				Iterator iterator = map.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry entry = (Entry) iterator.next(); 
					String key = entry.getKey().toString().toLowerCase();
					String val = entry.getValue().toString();
					result = result + key + ":'" + val + "',";
				}
				
				String childrenDate = getChildrenDate(list,map.get("ID").toString());
				
				if("[]".equals(childrenDate)){
					result = result + "leaf:true";
				}else{
					result = result + "leaf:false,children:" + childrenDate;
				}
				
				result = result + "},";
			}
		}
		
		if(result.lastIndexOf(",") == result.length()-1){
			result = result.substring(0, result.length()-1);
		}
		
		result = result + "]";
		return result;
	};*/
    
	/**
	 * replaceSTRN:(替换Java去除字符串中的空格、回车、换行符、制表符)
	 *
	 * @param oldStr			原字符串
	 * @return					转换完毕的字符串
	 * @author 					XZTeam
	 * @version 				Ver 1.0
	 * @since   				Ver 1.0
	 */
	public static String replaceSTRN(String oldStr) {
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(oldStr);
		return m.replaceAll("");
	}

	/**
	 * replaceTRN:(替换Java去除字符串中的回车、换行符、制表符 \t:制表符 \n:换行符 \r:回车符)
	 *
	 * @param oldStr			原字符串
	 * @return 					替换完毕的字符串
	 * @author 					XZTeam
	 * @version 				Ver 1.0
	 * @since   				Ver 1.0
	 */
	public static String replaceTRN(String oldStr) {
		Pattern p = Pattern.compile("\t|\r|\n");
		Matcher m = p.matcher(oldStr);
		return m.replaceAll("");
	}    
	
	/**
	 *  
	 *  特殊字符转义处理
	 *  
	 * @param s
	 * @return
	 */
	public static String string2Json(String s) {
		StringBuffer sb = new StringBuffer();
	    for (int i=0; i<s.length(); i++) {
	        char c = s.charAt(i);
	        switch (c) {
	        case '\'':
	            sb.append("\\\'");
	            break;
	        case '\\':
	            sb.append("\\\\");
	            break;
	        case '/':
	            sb.append("\\/");
	            break;
	        case '\b':
	            sb.append("\\b");
	            break;
	        case '\f':
	            sb.append("\\f");
	            break;
	        case '\n':
	            sb.append("\\n");
	            break;
	        case '\r':
	            sb.append("\\r");
	            break;
	        case '\t':
	            sb.append("\\t");
	            break;
	        default:
	            sb.append(c);
	        }
	    }
	    return sb.toString();
	 }
	
	public static String parseStoreJsonForExt4(int total, String flag, String metaData, String rows){
		StringBuffer json = new StringBuffer("{");

		json.append("'total':'").append(total).append("',");
		json.append("'success':").append(flag).append(",");
		json.append("'metaData':").append(metaData).append(",");
		json.append("'rows':[").append(rows).append("]");
		json.append("}");

		return json.toString();
	}
	/**
	 *过滤不存在子节点的记录 
	 * 
	 */
	public static void filterUserArray(ArrayList<HashMap> list,
			List<String> parentList,
			int arraySize) {
		for (int i = 0; i < list.size(); i++) {
			HashMap tmpHashMap = list.get(i);
			String id = (String)tmpHashMap.get("ID");
			if (!parentList.contains(id) && "M".equalsIgnoreCase((String)tmpHashMap.get("NODE_TYPE"))) {
				list.remove(tmpHashMap);
			}
		}
		if(arraySize != list.size()){
			parentList.clear();
			for (HashMap<String, String> a : list) {
				parentList.add(a.get("PARENT_ID"));
			}
			if (list.size() > 0)
				filterUserArray(list, parentList,list.size());
			
		}
	}
}

