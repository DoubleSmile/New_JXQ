package net.jxquan.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.jxquan.util.Counter;
import net.jxquan.util.ListUtils;
import net.sf.json.JSONArray;

import org.junit.Test;

public class ImageDAOTest {
	
	@Test
	public void fun(){
		List<Integer> lists=new ArrayList<Integer>();
		lists.add(1);
		lists.add(2);
		lists.add(3);
		String str=JSONArray.fromObject(lists).toString();
		JSONArray array=JSONArray.fromObject(str);
		List<Integer> list=(ArrayList<Integer>)JSONArray.toCollection(array);
		System.out.println(JSONArray.fromObject(list).toString());
		list.add(3);
		list.add(1);
		list.add(4);
		System.out.println(JSONArray.fromObject(ListUtils.toSetedList(list)).toString());
		
	}
	
	@Test
	public void test2(){
//		System.out.println(Counter.getPraiseRate(5, 3, 20,7,13));
	}

}
