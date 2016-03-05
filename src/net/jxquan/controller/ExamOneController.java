package net.jxquan.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jxquan.entity.ExamOne;
import net.jxquan.entity.ExamOneInfo;
import net.jxquan.entity.User;
import net.jxquan.service.ExamOneInfoService;
import net.jxquan.service.ExamOneService;
import net.jxquan.util.CookieUtil;
import net.jxquan.util.ExamWrapper;
import net.jxquan.util.JsonUtils;
import net.jxquan.util.ListUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/examOne",produces="text/html;charset=UTF-8")
public class ExamOneController {

	private static final Logger logger=Logger.getLogger(ExamOneController.class);
	private ExamOneService examOneService;

	public ExamOneService getExamOneService() {
		return examOneService;
	}
	
    @Autowired
	public void setExamOneInfoService(ExamOneInfoService examOneInfoService) {
		this.examOneInfoService = examOneInfoService;
	} 
    
    private ExamOneInfoService examOneInfoService;

	public ExamOneInfoService getExamOneInfoService() {
		return examOneInfoService;
	}
	
    @Autowired
	public void setExamOneService(ExamOneService examOneService) {
		this.examOneService = examOneService;
	} 
    
    @RequestMapping(value="/getExamByTurn",method={RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody String getExamByTurn(@RequestBody String requestJson,HttpServletRequest request){
    	JSONObject responseJson=new JSONObject();
		try{
			User user=(User)request.getSession().getAttribute("user");
			JSONObject requestJSON=JSONObject.fromObject(requestJson);
		    int turn=Integer.valueOf(requestJSON.getString("turn"));
	    	List<ExamOne> allExam=examOneService.getAllExam();
	    	ExamOne target=allExam.get(turn-1);
	    	ExamWrapper.wrapExam(responseJson, target);
	    	JsonUtils.setMsg(responseJson,true,"成功");
	    	return responseJson.toString();
		}catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			JsonUtils.setMsg(responseJson,false,"获取信息失败");
	    	return responseJson.toString();
		}
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/getExamRandomly",method={RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody String getExamRandomly(HttpServletRequest request){
		JSONObject responseJson=new JSONObject();
    	try{
	    	List<ExamOne> allExam=examOneService.getAllExam();
	    	List<Long> usedNumber;
	    	if(request.getSession().getAttribute("usedNumber") == null){
	    		usedNumber=new ArrayList<Long>();
	    	}else{
	    		usedNumber=(ArrayList<Long>)request.getSession().getAttribute("usedNumber");
	    	}
	    	ExamOne target=allExam.get(RandomUtils.nextInt(allExam.size()));
	    	while(usedNumber.contains(target.getId())){
	    		target=allExam.get(RandomUtils.nextInt(allExam.size()));
	    	}
	    	usedNumber.add(Long.valueOf(target.getId()));
	    	request.getSession().setAttribute("usedNumber",usedNumber);
	    	ExamWrapper.wrapExam(responseJson, target);
	    	JsonUtils.setMsg(responseJson,true,"成功");
	    	return responseJson.toString();
	    }catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			JsonUtils.setMsg(responseJson,false,"获取信息失败");
	    	return responseJson.toString();
		}
    }
    
	@RequestMapping(value="/imitate",method={RequestMethod.POST,RequestMethod.GET})
    public @ResponseBody String imitate(){
    	try{
    		Set<ExamOne> set=examOneService.imitate();
    		JSONArray array=new JSONArray();
    		for(ExamOne target:set){
    			JSONObject responseJson=new JSONObject();
    			ExamWrapper.wrapExam(responseJson, target);
		    	array.add(responseJson);
    		}
    		array.add(array.size());
	    	return array.toString();
	    }catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			JSONObject responseJson=new JSONObject();
			JsonUtils.setMsg(responseJson,false,"获取信息失败");
	    	return responseJson.toString();
		}
	}
    	
	@RequestMapping(value="/rememberLastExam",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String rememberUserMessage(@RequestBody String requestJson,HttpServletRequest request,HttpServletResponse response){
		JSONObject responseJson=new JSONObject();
		try{
			JSONObject requestJSON=JSONObject.fromObject(requestJson);
			User user=(User)request.getSession().getAttribute("user");
			int lastExam=Integer.valueOf(requestJSON.getString("lastExam"));
			ExamOneInfo examInfo =examOneInfoService.getExamInfoByUserId(user.getId());
			if(examInfo==null){
				examInfo=new ExamOneInfo();
				examInfo.setUserID(user.getId());
				examInfo.setLastExam(lastExam);
				examInfo.setWrongExam("x");
				examOneInfoService.addExamOneInfo(examInfo);
			}else{
				examInfo.setLastExam(lastExam);
				examOneInfoService.updateExamOneInfo(examInfo);
			}
			JsonUtils.setMsg(responseJson, true,"记录成功");
			return responseJson.toString();
		}catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			JsonUtils.setMsg(responseJson,false,"记录失败");
	    	return responseJson.toString();
		}
    }
	
	@RequestMapping(value="/getLastExam",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String getLastExam(@RequestBody String requestJson,HttpServletRequest request,HttpServletResponse response){
		JSONObject responseJson=new JSONObject();	
			try{
				User user=(User)request.getSession().getAttribute("user");
				ExamOneInfo examInfo=examOneInfoService.getExamInfoByUserId(user.getId());
				int examID=examInfo.getLastExam();
				ExamOne target=examOneService.getExamById(examID);
				ExamWrapper.wrapExam(responseJson, target);
				JsonUtils.setMsg(responseJson, true,"记录成功返回");
				return responseJson.toString();
			}catch(Exception e){
				logger.error("出现了萌萌哒错误", e);
				JsonUtils.setMsg(responseJson,false,"后台错误");
		    	return responseJson.toString();
			}
	    }
	
		@SuppressWarnings("unchecked")
		@RequestMapping(value="/rememberWrongExam",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody String rememberWrongExam(@RequestBody String requestJson,HttpServletRequest request,HttpServletResponse response){
			JSONObject responseJson=new JSONObject();
			try{
				JSONObject requestJSON=JSONObject.fromObject(requestJson);
				JSONArray wrongNumbers=requestJSON.getJSONArray("wrongNumbers");
				List list=JSONArray.toList(wrongNumbers);
				StringBuilder sb=new StringBuilder();
				for(int i =0;i<list.size()-1;i++){
					sb.append(list.get(i)+",");
				}
				sb.append(list.get(list.size()-1));
				User user=(User)request.getSession().getAttribute("user");
				ExamOneInfo examInfo=this.getExamOneInfoService().getExamInfoByUserId(user.getId());
				if(examInfo==null){
					examInfo=new ExamOneInfo();
					examInfo.setUserID(user.getId());
					examInfo.setLastExam(1);
					examInfo.setWrongExam(sb.toString());
					examOneInfoService.addExamOneInfo(examInfo);
				}else{
					examInfo.setWrongExam(sb.toString());
					examOneInfoService.updateExamOneInfo(examInfo);
				}
				JsonUtils.setMsg(responseJson, true,"更新成功");
				return responseJson.toString();	
			}catch(Exception e){
				logger.error("出现了萌萌哒错误", e);
				JsonUtils.setMsg(responseJson,false,"后台错误");
		    	return responseJson.toString();
			}
	    }
	
	/*@SuppressWarnings("unchecked")
	@RequestMapping(value="/removeWrongExam",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String removeWrongExam(@RequestBody String requestJson,HttpServletRequest request,HttpServletResponse response){
		JSONObject responseJson=new JSONObject();
		try{
			JSONObject requestJSON=JSONObject.fromObject(requestJson);
			int wrongNumber=Integer.valueOf(requestJSON.getString("wrongNumber"));
			User user=(User)request.getSession().getAttribute("user");
//			String cookieName=user.getMobile()+"-wrongNumbers";
			String cookieName="18829211793-wrongNumbers";
			if(CookieUtil.getCookie(request,cookieName)==null){
				JsonUtils.setMsg(responseJson, false,"都没有错误题你移除个毛线");
				return responseJson.toString();
			}else{
				Cookie cookie=CookieUtil.getCookie(request,cookieName);
				String wrongNumbers=cookie.getValue();
				JSONArray array=JSONArray.fromObject(wrongNumbers);
				List<Integer> list=(ArrayList<Integer>)JSONArray.toCollection(array);
				list=ListUtils.remove(list,wrongNumber);
				CookieUtil.setCookie(response,cookieName,JSONArray.fromObject(list).toString(),30*3600*24,"/","127.0.0.1");	
			}
			JsonUtils.setMsg(responseJson, true,"移除成功");
			return responseJson.toString();	
		}catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			JsonUtils.setMsg(responseJson,false,"后台错误");
	    	return responseJson.toString();
		}
    }*/
	
	@RequestMapping(value="/getWrongExam",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String getWrongExam(HttpServletRequest request,HttpServletResponse response){
		JSONObject responseJson=new JSONObject();	
			try{
				User user=(User)request.getSession().getAttribute("user");
				ExamOneInfo examInfo=examOneInfoService.getExamInfoByUserId(user.getId());
				JSONArray wrongNumbers=new JSONArray();
				String temp_num=examInfo.getWrongExam();
				String temp1_num=temp_num.replaceAll("[()]","");
				String[] temp2_num=temp1_num.split(",");
				for(String t:temp2_num){
					wrongNumbers.add(Integer.valueOf(t));
				}
				responseJson.put("wrongNumbers",wrongNumbers.toString());
				JsonUtils.setMsg(responseJson,true,"返回数据成功");
				return responseJson.toString();
			}catch(Exception e){
				logger.error("出现了萌萌哒错误", e);
				JsonUtils.setMsg(responseJson,false,"后台错误");
		    	return responseJson.toString();
			}
	    }
	
	
	@RequestMapping(value="/getNumberedExam",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String getNumberedExam(@RequestBody String requestJson,HttpServletRequest request,HttpServletResponse response){
		JSONObject responseJson=new JSONObject();	
			try{
				User user=(User)request.getSession().getAttribute("user");
				JSONObject requestJSON=JSONObject.fromObject(requestJson);
				int beginExam=requestJSON.getInt("beginExam");
				int number=requestJSON.getInt("number");
				List<ExamOne> exams=examOneService.getNumberedExam(beginExam, number);
				JSONArray array=new JSONArray();
				for(ExamOne target:exams){
	    			JSONObject responseJSON=new JSONObject();
	    			ExamWrapper.wrapExam(responseJSON, target);
			    	array.add(responseJSON);
	    		}
				responseJson.put("exam", array.toString());
				JsonUtils.setMsg(responseJson,true,"返回数据成功");
				return responseJson.toString();
			}catch(Exception e){
				logger.error("出现了萌萌哒错误", e);
				JsonUtils.setMsg(responseJson,false,"后台错误");
		    	return responseJson.toString();
			}
	    }
	
	@RequestMapping(value="/getGivenExam",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String getGivenExam(@RequestBody String requestJson,HttpServletRequest request,HttpServletResponse response){
		JSONObject responseJson=new JSONObject();	
			try{
				User user=(User)request.getSession().getAttribute("user");
				JSONObject requestJSON=JSONObject.fromObject(requestJson);
				JSONArray range=requestJSON.getJSONArray("range");
				String final_range=range.toString().replaceAll("[\\[\\]]","");
				List<ExamOne> exams=examOneService.getGivenExam(final_range);
				JSONArray array=new JSONArray();
				for(ExamOne target:exams){
	    			JSONObject responseJSON=new JSONObject();
	    			ExamWrapper.wrapExam(responseJSON, target);
			    	array.add(responseJSON);
	    		}
				responseJson.put("exam", array.toString());
				JsonUtils.setMsg(responseJson,true,"返回数据成功");
				return responseJson.toString();
			}catch(Exception e){
				logger.error("出现了萌萌哒错误", e);
				JsonUtils.setMsg(responseJson,false,"后台错误");
		    	return responseJson.toString();
			}
	    }
	
	
	
}
	
