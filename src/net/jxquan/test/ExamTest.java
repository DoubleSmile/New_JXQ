package net.jxquan.test;

import java.text.NumberFormat;

import net.jxquan.util.ExamOneUtils;

import org.junit.Test;

public class ExamTest {
	
	@Test
	public void fun() throws Exception{
//		ExamOneUtils.transformToSQL(ExamOneUtils.transform());
//	System.out.println(1);
	}
	
	@Test
	public void test1(){
		NumberFormat numberFormat=NumberFormat.getNumberInstance();
		numberFormat.setMaximumFractionDigits(2);
		double d=Double.valueOf(numberFormat.format(00.00));
		System.out.println(d);
	}

}
