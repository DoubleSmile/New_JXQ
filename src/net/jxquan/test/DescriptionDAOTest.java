package net.jxquan.test;


import net.jxquan.config.QiniuConfig;
import net.jxquan.util.QiniuUtils;

import org.junit.Test;

public class DescriptionDAOTest{
	
	@Test
	public void fun()throws Exception{
//		System.out.println(QiniuUtils.wrapUrl("asd", 1, 200,400));
//		SimpleLinkedList<T> list=new SimpleLinkedList<T>();
//		for(int i=0;i<10;i++){
//			T t=new T();
//			t.setId(i);
//			t.setName("Hello"+i);
//			t.setPassword("asf");
//			list.add(t);
//		}
//		System.out.println(list);
//		ListUtils.move(list,2);
//		System.out.println(list);
	    String url="asd";
	    System.out.println(QiniuUtils.wrapUrl(url,QiniuConfig.MODE, 200,200));
		System.out.println(url);
		
	}

}
