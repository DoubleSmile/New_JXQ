package net.jxquan.util;

import net.jxquan.entity.ExamFour;
import net.jxquan.entity.ExamOne;
import net.sf.json.JSONObject;


public class ExamWrapper {
	
	
	/**
	 * 用来包装Exam用以返回给前台或者Android
	 *
	 */
	public static JSONObject wrapExam(JSONObject responseJson,ExamOne target){
		responseJson.put("subject", 1);
		responseJson.put("id", target.getId());
    	responseJson.put("title",target.getTitle());
    	responseJson.put("imageUrl",target.getImageUrl());
    	responseJson.put("A",target.getA());
    	responseJson.put("B",target.getB());
    	responseJson.put("C",target.getC());
    	responseJson.put("D",target.getD());
    	responseJson.put("explain",target.getExplain());
    	if(target.getA()==null||target.getA().trim().equals("")){
    		responseJson.put("type", 1);
            if(target.getAnswer().equals("1")){
            	responseJson.put("answer","错误");
            	responseJson.put("android_answer",1);
            }
            else{
            	responseJson.put("answer","正确");
            	responseJson.put("android_answer",0);
            }
    	}
    	else{
    		responseJson.put("type", 0);
    		responseJson.put("answer",target.getAnswer());
    		responseJson.put("android_answer",target.getAnswer());
    	}
    	return responseJson;
	}
	
	/**
	 * 用来包装ExamFour用以返回给前台或者Android
	 *
	 */
	public static JSONObject wrapExamFour(JSONObject responseJson,ExamFour target){
		responseJson.put("subject", 4);
		responseJson.put("id", target.getId());
    	responseJson.put("title",target.getTitle());
    	responseJson.put("imageUrl",target.getImageUrl());
    	responseJson.put("A",target.getA());
    	responseJson.put("B",target.getB());
    	responseJson.put("C",target.getC());
    	responseJson.put("D",target.getD());
    	responseJson.put("explain",target.getExplain());
    	if(target.getA()==null||target.getA().trim().equals("")){
    		responseJson.put("type", 1);
            if(target.getAnswer().equals("1")){
            	responseJson.put("answer","错误");
            	responseJson.put("android_answer",1);
            }
            else{
            	responseJson.put("answer","正确");
            	responseJson.put("android_answer",0);
            }
    	}
    	else{
    		responseJson.put("type", 0);
    		responseJson.put("answer",target.getAnswer());
    		responseJson.put("android_answer",target.getAnswer());
    	}
    	return responseJson;
	}

}
