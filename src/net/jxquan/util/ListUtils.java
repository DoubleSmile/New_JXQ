package net.jxquan.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.jxquan.test.T;

/**
 * @author luckyyflv
 *
 */
public class ListUtils {
	
	/**
	 * 将List里面的数据去重(类似于实现Set)
	 */
	@SuppressWarnings("rawtypes")
	public static List toSetedList(List list){
		Set set=new HashSet();
		List newList=new ArrayList();
		for(Object key:list){
			set.add(key);
		}
		for(Object listKey:set){
			newList.add(listKey);
		}
		return newList;
		
	}
	
	/**
	 * 强化List用于移除相关数据(主要针对INT型数据)
	 */
	@SuppressWarnings("rawtypes")
	public static List remove(List list,Object target){
		List newList=new ArrayList();
		for(Object obj:list){
			if(!obj.equals(target))
				newList.add(obj);
		}
		return newList;
	}

}
