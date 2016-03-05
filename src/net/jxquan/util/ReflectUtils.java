package net.jxquan.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectUtils {
	
	public static String changeToSet(String target){
	    StringBuilder sb=new StringBuilder();
	    sb.append("set");
	    String firstChar=target.toUpperCase().substring(0, 1);
	    sb.append(firstChar);
	    sb.append(target.substring(1));
	    return sb.toString();
	}
	
	public static String changeToGet(String target){
	    StringBuilder sb=new StringBuilder();
	    sb.append("get");
	    String firstChar=target.toUpperCase().substring(0, 1);
	    sb.append(firstChar);
	    sb.append(target.substring(1));
	    return sb.toString();
	}
	
	public static Map<String,String> getValues(Object target){
		String className=target.getClass().getName();
			Class clazz=null;
			HashMap map=new HashMap();
			try {
				clazz = Class.forName(className);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("给出的类有问题！");
			}
			Field[] fields=clazz.getDeclaredFields();
			Method[] methods=clazz.getDeclaredMethods();
			for(Method m:methods){
				for(Field f:fields){
					if(m.getName().equals(changeToGet(f.getName().toString())) && !(m.getName().equals("getId"))){
						try {
							map.put(f.getName(), m.invoke(target));
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			return map;
	}

}
