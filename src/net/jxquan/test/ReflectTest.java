package net.jxquan.test;

import net.jxquan.util.CheckCodeUtils;
import net.jxquan.util.CloneUtils;
import net.jxquan.util.ReflectUtils;

import org.junit.Test;

public class ReflectTest {
	@Test
	public void fun(){
//		T t1=new T();
//		t1.setId(2);
//		T t2=new T(1,"我就是账号","你就是密码");
//		CloneUtils.clone(t1, t2);
//		System.out.println(t1.toString());
////		System.out.println(ReflectUtils.getValues(t2).toString());
		for(int i=0;i<100;i++){
			
			System.out.println(CheckCodeUtils.getCheckCode());
		}
		
	}

}
