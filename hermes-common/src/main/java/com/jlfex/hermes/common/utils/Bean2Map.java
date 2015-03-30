package com.jlfex.hermes.common.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Bean2Map {

	public static  Map<String, String> getValueMap(Object obj) {  
	     Map<String, String> map = new HashMap<String, String>();  
	     Field[] fields = obj.getClass().getDeclaredFields();  
	     for (int i = 0, len = fields.length; i < len; i++) {  
	         String varName = fields[i].getName();  
	         try {  
	             // 获取原来的访问控制权限  
	             boolean accessFlag = fields[i].isAccessible();  
	             // 修改访问控制权限  
	             fields[i].setAccessible(true);  
	             // 获取在对象f中属性fields[i]对应的对象中的变量  
	             Object o = fields[i].get(obj);  
	             if(o != null){
	            	 map.put(varName, o.toString()); 
	             }
	             // 恢复访问控制权限  
	             fields[i].setAccessible(accessFlag);  
	         } catch (IllegalArgumentException ex) {  
	             ex.printStackTrace();  
	         } catch (IllegalAccessException ex) {  
	             ex.printStackTrace();  
	         }  
	     }  
	     return map;  
	 }  
}
