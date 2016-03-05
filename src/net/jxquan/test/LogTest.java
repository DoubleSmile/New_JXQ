package net.jxquan.test;

import org.junit.Test;

import net.jxquan.util.Log;

public class LogTest {

	@Test
	public void fun(){
		Log log=Log.getLogger();
		try{
			double i =1/0;
		}catch(Exception e){
			log.logger.error("Error Message",e);
		}
		
	}
}
