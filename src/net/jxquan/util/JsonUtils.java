package net.jxquan.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class JsonUtils {
	
	/**
	 * 用户出错后向前台返回相应信息
	 */
	public static void setMsg(JSONObject responseJson,boolean isSuccessful,String errorMsg){
		responseJson.put("success",isSuccessful);
		responseJson.put("msg",errorMsg);
	}
	
}
