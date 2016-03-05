package net.jxquan.util;

import java.util.Comparator;

import net.sf.json.JSONObject;

public class TopComparator implements Comparator<JSONObject>{

	@Override
	public int compare(JSONObject o1, JSONObject o2) {
		// TODO Auto-generated method stub
		double result_1=Counter.getSchoolPraiseRate(o1.getInt("ownStudent"), o1.getInt("otherStudent"), o1.getInt("totalStudent"),o1.getInt("schoolOrders"), o1.getInt("totalOrders"));
		double result_2=Counter.getSchoolPraiseRate(o2.getInt("ownStudent"), o2.getInt("otherStudent"), o2.getInt("totalStudent"),o2.getInt("schoolOrders"), o2.getInt("totalOrders"));
		if(result_1<result_2)
			return 1;
		else if(result_1==result_2 && o1.getLong("schoolID")==o2.getLong("schoolID"))
			return 0;
		else
			return -1;
		
	}

}
