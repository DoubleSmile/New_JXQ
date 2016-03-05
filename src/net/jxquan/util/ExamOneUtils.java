package net.jxquan.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


public class ExamOneUtils {
	
	
    public static List<Map<String,Object>> transform() throws Exception{
    	BufferedReader reader=new BufferedReader(new FileReader(new File("G://New_JXQ//k4.json")));
    	String line=null;
    	StringBuilder sb=new StringBuilder();
    	List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
    	while((line=reader.readLine()) != null){
    	      sb.append(line);
    	}
    	JSONArray array=JSONArray.fromObject(sb.toString());
    	
    	for(int i=0;i<array.size();i++){
    		Map<String,Object> map=new HashMap<String,Object>();
    		JSONObject json=JSONObject.fromObject(array.get(i));
    		String title=json.getString("title");
    		String imageUrl;
    		try{
    			imageUrl=json.getString("image");
    		}catch(JSONException e){
    			imageUrl=null;
    		}
    		
    		String A;
    		try{
    			A=json.getString("A");
    		}catch(JSONException e){
    			A=null;
    		}
    		
    		String B;
    		try{
    			B=json.getString("B");
    		}catch(JSONException e){
    			B=null;
    		}
    		
    		String C;
    		try{
    			C=json.getString("C");
    		}catch(JSONException e){
    			C=null;
    		}
    		
    		String D;
    		try{
    			D=json.getString("D");
    		}catch(JSONException e){
    			D=null;
    		}
    		
    		String explain;
    		try{
    			explain=json.getString("explain");
    		}catch(JSONException e){
    			explain=null;
    		}
    		
    		String answer;
    		try{
    			answer=json.getString("answer");
    		}catch(JSONException e){
    			answer=null;
    		}
    		if(title==null ||title.trim().equals(""))
    			map.put("title",null);
    		else
    			map.put("title",title);
    		
    		if(imageUrl==null ||imageUrl.trim().equals(""))
    			map.put("imageUrl",null);
    		else{
    			map.put("imageUrl",imageUrl);
    			
    		}
    		
    		if(A==null ||A.trim().equals(""))
    			map.put("A",null);
    		else
    			map.put("A",A);

    		if(B==null ||B.trim().equals(""))
    			map.put("B",null);
    		else
    			map.put("B",B);

    		if(C==null ||C.trim().equals(""))
    			map.put("C",null);
    		else
    			map.put("C",C);

    		if(D==null ||D.trim().equals(""))
    			map.put("D",null);
    		else
    			map.put("D",D);

    		if(answer==null ||answer.trim().equals(""))
    			map.put("answer",null);
    		else
    			map.put("answer",answer);
    		
    		if(explain==null ||explain.trim().equals(""))
    			map.put("explain",null);
    		else
    			map.put("explain",explain);
    		int j=i+1;
    		map.put("id",j);
    		map.put("examType",0);
    		map.put("difficulty",0);
    		list.add(map);
    	}
    	reader.close();
    	return list;
	}
	
	public static void transformToSQL(List<Map<String,Object>> list)throws Exception{
		BufferedWriter writer=new BufferedWriter(new FileWriter(new File("G://New_JXQ/examFour.sql")));
		StringBuilder sb=new StringBuilder();
		sb.append("INSERT INTO examFour VALUES ");
		sb.append("\r\n");
		for(Map m:list){
			sb.append("(");
			sb.append(m.get("id"));
			
			sb.append(", '");
			sb.append(m.get("title"));
			sb.append("'");
			
			sb.append(", '");
			sb.append(m.get("imageUrl"));
			sb.append("'");
			
			sb.append(", '");
			sb.append(m.get("A"));
			sb.append("'");
			
			sb.append(", '");
			sb.append(m.get("B"));
			sb.append("'");
			
			sb.append(", '");
			sb.append(m.get("C"));
			sb.append("'");
			
			sb.append(", '");
			sb.append(m.get("D"));
			sb.append("'");
			
			sb.append(", '");
			sb.append(m.get("answer"));
			sb.append("'");
			
			sb.append(", '");
			sb.append(m.get("explain"));
			sb.append("'");
			
			sb.append(", ");
			sb.append(m.get("examType"));
			
			sb.append(", ");
			sb.append(m.get("difficulty"));
			sb.append("),");
			sb.append("\r\n");
		}
		writer.write(sb.toString());
		writer.flush();
		writer.close();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
