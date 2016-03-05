package net.jxquan.test;

import org.junit.Test;

public class SchoolDAOTest {
	
	@Test
	public void test(){
		
		String test="\""+"[123]"+"\"";
		System.out.println(test.replaceAll("(\"(?=\\[)|((?<=\\])\"))",""));
//		System.out.println(test.replaceAll("(?<=\\])\"",""));
	
	}

}
