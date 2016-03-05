package net.jxquan.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.jxquan.entity.School;
import net.jxquan.entity.User;
import net.jxquan.service.AdminService;
import net.jxquan.service.SchoolService;
import net.jxquan.service.UserService;
import net.jxquan.util.CheckCodeUtils;
import net.jxquan.util.JsonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author luckyyflv
 * @Date  2015/5/26
 */
@Controller
@RequestMapping(value="/admin",produces="text/html;charset=UTF-8")
public class AdminController {
	private static final Logger logger=Logger.getLogger(AdminController.class);

	private UserService userService;
	
	public UserService getUserService() {
		return userService;
	}
    @Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	private SchoolService schoolService;

	public SchoolService getSchoolService() {
		return schoolService;
	}
	@Autowired
	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}
    private AdminService adminService;
	
	public AdminService getAdminService() {
		return adminService;
	}
    @Autowired
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
    
	@RequestMapping(value="/getAllUsers",method=RequestMethod.POST)
	public @ResponseBody String getAllUsers(){
		JSONObject responseJson=new JSONObject();
		try{
			List<User> list=adminService.getAllUsers();
			JSONArray array=JSONArray.fromObject(list);
			return array.toString();
		}catch(Exception e){
			logger.error("出现了萌萌哒错误！",e);
			JsonUtils.setMsg(responseJson,false,"服务器出错");
			return responseJson.toString();
		}
	}
	
	@RequestMapping(value="/addUser",method=RequestMethod.POST)
	public @ResponseBody String addUser(@RequestBody String requestJson,HttpServletRequest request){
		JSONObject responseJson=new JSONObject();
		try{
			JSONObject requestJSON=JSONObject.fromObject(requestJson);
			String mobile=requestJSON.getString("mobile");
			String password=requestJSON.getString("password");
			if((userService.findByMobile(mobile))!=null){
				JsonUtils.setMsg(responseJson,false,"该号码已被注册");
				return responseJson.toString();
			}
			userService.register(mobile, password,CheckCodeUtils.getCheckCode().toString());
			JsonUtils.setMsg(responseJson,true,"添加成功");
			return responseJson.toString();
		}catch(Exception e){
			logger.error("出现了萌萌哒错误！",e);
			JsonUtils.setMsg(responseJson,false,"服务器出错");
			return responseJson.toString();
		}
	}
	
	@RequestMapping(value="/deleteUser",method=RequestMethod.POST)
	public @ResponseBody String deleteUser(@RequestBody String requestJson,HttpServletRequest request){
		JSONObject responseJson=new JSONObject();
		try{
			JSONObject requestJSON=JSONObject.fromObject(requestJson);
			String mobile=requestJSON.getString("mobile");
			if((userService.findByMobile(mobile))== null){
				JsonUtils.setMsg(responseJson,false,"不存在所删除的用户信息");
				return responseJson.toString();
			}
			userService.deleteUser(Long.valueOf(mobile));
			JsonUtils.setMsg(responseJson,true,"删除成功");
			return responseJson.toString();
		}catch(Exception e){
			logger.error("出现了萌萌哒错误！",e);
			JsonUtils.setMsg(responseJson,false,"服务器出错");
			return responseJson.toString();
		}
	}
	
	@RequestMapping(value="/acceptSchool",method=RequestMethod.GET)
	public @ResponseBody String acceptSchool(@RequestBody String requestJson,HttpServletRequest request){
		JSONObject responseJson=new JSONObject();
		try{
			JSONObject requestJSON=JSONObject.fromObject(requestJson);
			long schoolID=Long.valueOf(requestJSON.getString("schoolID"));
			try{
				schoolService.acceptSchool(schoolID);
			}catch(Exception e){
				JsonUtils.setMsg(responseJson, false,"没有该学校对应的信息,请核对后再请求");
				return responseJson.toString();
			}
			JsonUtils.setMsg(responseJson, true,"通过验证");
			return responseJson.toString();
		}catch(Exception e){
			logger.error("出现了萌萌哒错误！",e);
			JsonUtils.setMsg(responseJson,false,"服务器出错");
			return responseJson.toString();			
		}
	}
	
	@RequestMapping(value="/listUncheckedSchool",method=RequestMethod.POST)
	public @ResponseBody String listUnckeckedSchool(HttpServletRequest request){
		JSONObject responseJson=new JSONObject();
		try{
			List<School> schools=schoolService.getUnckeckedSchool();
			responseJson.put("schools",schools);
			return responseJson.toString();
		}catch(Exception e){
			logger.error("出现了萌萌哒错误！",e);
			JsonUtils.setMsg(responseJson,false,"服务器出错");
			return responseJson.toString();			
		}
	}
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

