package net.jxquan.util;

import java.text.DateFormat;
import java.text.NumberFormat;


/**
 * @author luckyyflv
 * @date  2015/7/27
 */

public class Counter {

	/**
	 * 计算最终驾校的排名值
	 */
	public static double getSchoolPraiseRate(int ownStudent,int otherStudent,int totalStudent,int schoolOrders,int totalOrders){
		double d_ownStudent=new Integer(ownStudent).doubleValue();
		double d_otherStudent=new Integer(otherStudent).doubleValue();
		double d_totalStudent=new Integer(totalStudent).doubleValue();
		double d_schoolOrders=new Integer(schoolOrders).doubleValue();
		double d_totalOrders=new Integer(totalOrders).doubleValue();
		double result;
		try{
			result=((d_ownStudent+d_otherStudent)/d_totalStudent*30.00)+(d_schoolOrders/d_totalOrders*70.00);
		}catch(Exception e){
			result=0.00;
		}
		NumberFormat numberFormat=NumberFormat.getNumberInstance();
		numberFormat.setMaximumFractionDigits(2);
		double finalResult;
		try{
			finalResult=Double.valueOf(numberFormat.format(result));
		}catch(NumberFormatException e){
			finalResult=0.00;
		}
		return finalResult;
	}
	
	/**
	 * 计算最终教练的排名值
	 */
	public static double getCoachPraiseRate(int ownStudent,int otherStudent,int totalStudent,int schoolOrders,int totalOrders){
		double d_ownStudent=new Integer(ownStudent).doubleValue();
		double d_otherStudent=new Integer(otherStudent).doubleValue();
		double d_totalStudent=new Integer(totalStudent).doubleValue();
		double d_schoolOrders=new Integer(schoolOrders).doubleValue();
		double d_totalOrders=new Integer(totalOrders).doubleValue();
		double reslut=((d_ownStudent+d_otherStudent)/d_totalStudent*30.00)+(d_schoolOrders/d_totalOrders*70.00);
		NumberFormat numberFormat=NumberFormat.getNumberInstance();
		numberFormat.setMaximumFractionDigits(2);
		double finalResult=Double.valueOf(numberFormat.format(reslut));
		return finalResult;
	}
}
