package net.jxquan.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author luckyyflv
 *
 * @param <T>
 */

public class CloneUtils{
	
	/**
	 * 用于实现类似于tempUser.clone(user)
	 * 这个给出的user是一个瞬时态的对象，而tempUser是持久态对象，
	 * 现在要将user的值都一一赋给tempUser但是不改变tempUser的id
	 */
	public static void clone(Object source,Object target){
		if(source.getClass() == target.getClass()){
			String className=target.getClass().getName();
			Map map=ReflectUtils.getValues(target);
			try {
				Class clazz=Class.forName(className);
				Field[] fields=clazz.getDeclaredFields();
				Method[] methods=clazz.getDeclaredMethods();
				for(Method m:methods){
					for(Field f:fields){
						if(m.getName().equals(ReflectUtils.changeToSet(f.getName())) && !(m.getName().equals("setId"))
								&& !(m.getName().equals("setMobile"))&& !(m.getName().equals("setPassword"))){
							try {
								if(map.get(f.getName())!=null)
								m.invoke(source,map.get(f.getName()));
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
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException("对象的ClassName有问题喔！");
			}
		}else{
			throw new RuntimeException("对象无法进行克隆，请检查对象类型是否正确");
		}
	   
	}
	
	public void clone(Object target,Object source,String methodName){
		
	}
	

}
