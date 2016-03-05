package net.jxquan.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jxquan.entity.SchoolPraise;
import net.jxquan.entity.User;
import net.jxquan.service.AppointmentService;
import net.jxquan.service.CarService;
import net.jxquan.service.OrderService;
import net.jxquan.service.SchoolPraiseService;
import net.jxquan.service.SchoolService;
import net.jxquan.service.UserService;
import net.jxquan.util.CheckCodeUtils;
import net.jxquan.util.CookieUtil;
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
@RequestMapping(value="/user",produces="text/json;charset=UTF-8")
public class UserController {
	private static final Logger logger=Logger.getLogger(UserController.class);

	private UserService userService;
	
	public UserService getUserService() {
		return userService;
	}
    @Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
    private AppointmentService appointmentService;
	
	public AppointmentService getAppointmentService() {
		return appointmentService;
	}
    @Autowired
	public void SetAppointmentService(AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}
    private OrderService orderService;
    public OrderService getOrderService() {
		return orderService;
	}
    @Autowired
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
    
    private SchoolPraiseService schoolPraiseService;
    public SchoolPraiseService getSchoolPraiseService() {
		return schoolPraiseService;
	}
    @Autowired
	public void setSchoolPraiseService(SchoolPraiseService schoolPraiseService) {
		this.schoolPraiseService = schoolPraiseService;
	}
    
    private SchoolService schoolService;
    public SchoolService getSchoolService() {
		return schoolService;
	}
    @Autowired
	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}
    
    private CarService carService;
    public CarService getCarService() {
		return carService;
	}
    @Autowired
	public void setCarService(CarService carService) {
		this.carService = carService;
	}
	
	@RequestMapping(value="/sendCheckCode",method=RequestMethod.POST)
	public @ResponseBody String sendCheckCode(@RequestBody String requestJson,HttpServletRequest request){
		JSONObject responseJson=new JSONObject();
     try{
    	 JSONObject requestJSON=JSONObject.fromObject(requestJson);
		String mobile=requestJSON.getString("mobile");
		Object lastTimeObj=request.getSession().getAttribute("curTime");
		String lastTime=null;
		if(lastTimeObj==null){
			lastTime="0";
		}else{
			lastTime=lastTimeObj.toString();
		}
		Long curTime=System.currentTimeMillis();
		if(curTime-Long.valueOf(lastTime)<1*60*1000){
			responseJson.put("success",false);
            responseJson.put("msg", "两次请求时间间隔小于1分钟");//表示两次访问时间间隔过短，不予访问（可能是JS脚本的攻击）
            return responseJson.toString();
		}
		if((userService.findByMobile(mobile))!=null){
			responseJson.put("success",false);
			responseJson.put("msg","该电话号码已经被注册！");
			return responseJson.toString();
		}
		Integer checkCode=CheckCodeUtils.getCheckCode();
		//将checkCode发到前台之后需要前台进行配合，使用户在输入验证码不对的情况下不能点击提交
		CheckCodeUtils.sendCheckCode(checkCode, mobile);
		responseJson.put("curTime",curTime);
		responseJson.put("requestMobile",mobile);
		request.getSession().setAttribute("checkCode", checkCode);
		request.getSession().setAttribute("curTime", curTime.toString());
		responseJson.put("success", true);
		return responseJson.toString();
     }catch(Exception e){
    	 logger.error("出现了萌萌哒错误", e);
    	 responseJson.put("success",false);
    	 responseJson.put("msg","服务器错误！");
    	 return responseJson.toString();
     }
    
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public @ResponseBody String register(@RequestBody String requestJson,HttpServletRequest request){
		JSONObject responseJson=new JSONObject();
		try{
			JSONObject requestJSON=JSONObject.fromObject(requestJson);
			String requestMobile=requestJSON.getString("requestMobile");
			String mobile=requestJSON.getString("mobile");
			if(!requestMobile.equals(mobile)){
				responseJson.put("success",false);
				responseJson.put("msg","短信发送的手机号与注册手机号不符,请再次确认两次号码一致");
				return responseJson.toString();
			}
			long lastTime=Long.valueOf(requestJSON.getString("curTime"));
			long curTime=System.currentTimeMillis();
			if(curTime-lastTime>1*60*1000){
				responseJson.put("success",false);
	            responseJson.put("msg", "验证码失效");//表示两次访问时间间隔过短，不予访问（可能是JS脚本的攻击）
	            return responseJson.toString();
			}
			String password=requestJSON.getString("password");
			String requestCheckCode=requestJSON.getString("checkCode");
			String responseCheckCode=request.getSession().getAttribute("checkCode").toString();
			if(!requestCheckCode.equals(responseCheckCode)){
				responseJson.put("success",false);
				responseJson.put("msg","注册码有误！");
				return responseJson.toString();
			}
			if((userService.findByMobile(mobile))!=null){
				responseJson.put("success",false);
				responseJson.put("msg","该电话号码已经被注册！");
				return responseJson.toString();
			}
			userService.register(mobile, password,requestCheckCode);
			responseJson.put("success",true);
			responseJson.put("msg","注册成功");
			return responseJson.toString();
		}catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			responseJson.put("success",false);
			responseJson.put("msg","服务器出错！");
			return responseJson.toString();
		}
	}
	
	
	@RequestMapping(value="/sendResetCode",method=RequestMethod.POST)
	public @ResponseBody String sendResetCode(@RequestBody String requestJson,HttpServletRequest request){
		JSONObject responseJson=new JSONObject();
     try{
    	 JSONObject requestJSON=JSONObject.fromObject(requestJson);
		String mobile=requestJSON.getString("mobile");
		Object lastTimeObj=request.getSession().getAttribute("curTime");
		String lastTime=null;
		if(lastTimeObj==null){
			lastTime="0";
		}else{
			lastTime=lastTimeObj.toString();
		}
		Long curTime=System.currentTimeMillis();
		if(curTime-Long.valueOf(lastTime)<1*60*1000){
			responseJson.put("success",false);
            responseJson.put("msg", "两次请求时间间隔小于1分钟");//表示两次访问时间间隔过短，不予访问（可能是JS脚本的攻击）
            return responseJson.toString();
		}
		if((userService.findByMobile(mobile))==null){
			responseJson.put("success",false);
			responseJson.put("msg","该电话号码未被注册！");
			return responseJson.toString();
		}
		Integer checkCode=CheckCodeUtils.getCheckCode();
		//将checkCode发到前台之后需要前台进行配合，使用户在输入验证码不对的情况下不能点击提交
		CheckCodeUtils.sendCheckCode(checkCode, mobile);
		responseJson.put("curTime",curTime);
		responseJson.put("requestMobile",mobile);
		request.getSession().setAttribute("checkCode", checkCode);
		request.getSession().setAttribute("curTime", curTime.toString());
		responseJson.put("success", true);
		responseJson.put("msg","验证码已发送,请注意查收!");
		return responseJson.toString();
     }catch(Exception e){
    	 logger.error("出现了萌萌哒错误", e);
    	 responseJson.put("success",false);
    	 responseJson.put("msg","服务器错误！");
    	 return responseJson.toString();
     }
	}
	
	@RequestMapping(value="/forgetPassword",method=RequestMethod.POST)
	public @ResponseBody String forgetPassword(@RequestBody String requestJson,HttpServletRequest request){
		JSONObject responseJson=new JSONObject();
		try{
			JSONObject requestJSON=JSONObject.fromObject(requestJson);
			String requestMobile=requestJSON.getString("requestMobile");
			String mobile=requestJSON.getString("mobile");
			if(!requestMobile.equals(mobile)){
				responseJson.put("success",false);
				responseJson.put("msg","短信发送的手机号与注册手机号不符,请再次确认两次号码一致");
				return responseJson.toString();
			}
			long lastTime=Long.valueOf(requestJSON.getString("curTime"));
			long curTime=System.currentTimeMillis();
			if(curTime-lastTime>1*60*1000){
				responseJson.put("success",false);
	            responseJson.put("msg", "验证码失效");//表示两次访问时间间隔过短，不予访问（可能是JS脚本的攻击）
	            return responseJson.toString();
			}
			String password=requestJSON.getString("password");
			String requestCheckCode=requestJSON.getString("checkCode");
			String responseCheckCode=request.getSession().getAttribute("checkCode").toString();
			if(!requestCheckCode.equals(responseCheckCode)){
				responseJson.put("success",false);
				responseJson.put("msg","注册码有误！");
				return responseJson.toString();
			}
			User user=userService.findByMobile(mobile);
			User updateUser=userService.findByID(user.getId());
			updateUser.setCheckCode(responseCheckCode);
			updateUser.setPassword(password);
			userService.updateUserMsg(updateUser);
			responseJson.put("success",true);
			responseJson.put("msg","重置密码成功");
			return responseJson.toString();
		}catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			responseJson.put("success",false);
			responseJson.put("msg","服务器出错！");
			return responseJson.toString();
		}
	}
	
	@RequestMapping(value="/resetPassword",method=RequestMethod.POST)
	public @ResponseBody String resetPassword(@RequestBody String requestJson,HttpServletRequest request){
		JSONObject responseJson=new JSONObject();
		try{
			JSONObject requestJSON=JSONObject.fromObject(requestJson);
			String mobile=requestJSON.getString("mobile");
			String oldPassword=requestJSON.getString("oldPassword");
			String newPassword=requestJSON.getString("newPassword");
			User user=(User)request.getSession().getAttribute("user");
			if(user==null){
				responseJson.put("success",false);
				responseJson.put("msg","请先登录之后然后再改密码");
			}
			User targetUser=userService.findByID(user.getId());
			if(targetUser==null){
				responseJson.put("success",false);
				responseJson.put("msg","您输入的密码不正确，请确认后重新输入");
			}
			targetUser.setPassword(newPassword);
			userService.updateUserMsg(targetUser);
			
			responseJson.put("success",true);
			responseJson.put("msg","重置密码成功");
			return responseJson.toString();
		}catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			responseJson.put("success",false);
			responseJson.put("msg","服务器出错！");
			return responseJson.toString();
		}
	}
	
	@RequestMapping(value="/login",method={RequestMethod.POST,RequestMethod.OPTIONS})
	public @ResponseBody String  login(@RequestBody String requestJson,HttpServletRequest request,HttpServletResponse response){
		JSONObject responseJson=new JSONObject();
		try{
			JSONObject requestJSON=JSONObject.fromObject(requestJson);
			String mobile=requestJSON.getString("phone");
			String password=requestJSON.getString("password");
			responseJson.put("type","user");
			if(userService.findByMobile(mobile) == null){
				responseJson.put("success",false);
				responseJson.put("msg","您输入的电话号码没有注册！");
				return responseJson.toString();
			}
			else if(userService.findByMobileAndPassword(mobile,password) == null){
				responseJson.put("success",false);
				responseJson.put("msg","您输入的密码有误");
				return responseJson.toString();
			}
		else {
			User user=userService.findByMobile(mobile);
			responseJson.put("phone",user.getMobile());
			responseJson.put("userID",user.getId());
			request.getSession().setAttribute("user", user);
			request.getSession().setMaxInactiveInterval(60*15);
			CookieUtil.setCookie(response, "mobile",String.valueOf(user.getMobile()), 3600*24*7, "/","127.0.0.1");
			responseJson.put("success", true);
			responseJson.put("msg","登陆成功");
			responseJson.put("mobile",mobile);
			responseJson.put("password", password);
			return responseJson.toString();
			}
		}catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			responseJson.put("success",false);
			responseJson.put("msg","服务器出错！");
            return responseJson.toString();
			
 		}
	}
	   //用户注销	
		@RequestMapping(value="/logout",method=RequestMethod.GET)
		public @ResponseBody String loginOut(HttpServletRequest request,HttpServletResponse response){
			JSONObject responseJson=new JSONObject();
			try{
				Cookie[] cookies=request.getCookies();
				for(Cookie c:cookies){
					if(c.getName().equals("mobile")){
						CookieUtil.setCookie(response,"mobile",null,1, "/","localhost");
					}
				}
				request.getSession().removeAttribute("user");
				responseJson.put("success",true);
				return responseJson.toString();
			}catch(Exception e){
				logger.error("出现了萌萌哒错误", e);
				responseJson.put("success",false);
				responseJson.put("msg","注销失败！");
				return responseJson.toString();
			}
		}
		
	@RequestMapping(value="/getUserDetail",method=RequestMethod.POST)
	public @ResponseBody String getUserDetail(HttpServletRequest request){
		JSONObject responseJson=new JSONObject();
		try{
			User user=(User) request.getSession().getAttribute("user");
			responseJson.put("phone",user.getMobile());
			if(user.getGender() == 0)
			responseJson.put("agenda",0);
			else
				responseJson.put("agenda",1);
			responseJson.put("age",user.getAge());
			responseJson.put("weChat",user.getWeChat());
			if(user.getIsLocal() == 0)
				responseJson.put("isLocal",0);
			else
				responseJson.put("isLocal",1);
			responseJson.put("des",user.getDescription());
			responseJson.put("address",user.getAddress());
			return responseJson.toString();
		}catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			responseJson.put("success",false);
			responseJson.put("msg","服务器出错！");
			return responseJson.toString();
		}
	}
	
	@RequestMapping(value="/updateUser",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String updateUser(@RequestBody String requestJson,HttpServletRequest request){
		JSONObject reponseJson=new JSONObject();
		try{
			JSONObject requestJSON=JSONObject.fromObject(requestJson);
			User user=new User();
			Long userID=Long.valueOf(requestJSON.getString("id"));
			user.setId(userID);
			String weChat=requestJSON.getString("weChat");
			user.setWeChat(weChat);
			Integer gender=Integer.valueOf(requestJSON.getString("agenda"));
			if(gender == 0)
				user.setGender(0);
			else
				user.setGender(1);
			int age=Integer.valueOf(requestJSON.getString("age"));
			user.setAge(age);
			Integer isLocal=Integer.valueOf(requestJSON.getString("isLocal"));
			if(isLocal ==0)
				user.setIsLocal(0);
			else
				user.setIsLocal(1);
			user.setDescription(requestJSON.getString("des"));
			user.setAddress(requestJSON.getString("address"));
			user.setValid(user.getValid());
			userService.updateUser(user);
			User curUser=userService.findByID(userID);
			request.getSession().setAttribute("user",curUser);
			reponseJson.put("success",true);
			return reponseJson.toString();
		}catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			reponseJson.put("success",false);
			reponseJson.put("msg","更新失败！");
			return reponseJson.toString();
		}
	}
	
	@RequestMapping(value="/addSchoolPraise",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String makeSchoolPraise(@RequestBody String requestJson,HttpServletRequest request){
		JSONObject reponseJson=new JSONObject();
		try{
			JSONObject requestJSON=JSONObject.fromObject(requestJson);
			User user=(User)request.getSession().getAttribute("user");
			if(user.getValid()==1){
				reponseJson.put("success",false);
				reponseJson.put("msg","今天已经点过赞了骚年，您可以明天再点赞喔");
				return reponseJson.toString();
			}
			long schoolID=Long.valueOf(requestJSON.getString("schoolID"));
			List<Long> allUsers=orderService.getUsersBySchoolID(schoolID);
			SchoolPraise sp=schoolPraiseService.getSchoolPraiseBySchoolID(schoolID);
			if(sp==null){
				sp=new SchoolPraise();
				sp.setPraiseAmount(0);
				sp.setPariseRate(00.00);
				sp.setSchoolID(schoolID);
				//如果是本校学生的话
				if(allUsers.contains(user.getId())){
					sp.setOwnStudent(1);
					sp.setOtherStudent(0);
					sp.setTotalStudent(Integer.valueOf(Long.toString(orderService.getOrderCountBySchoolID(schoolID))));
				}else{
					sp.setOwnStudent(0);
					sp.setOtherStudent(1);
					sp.setTotalStudent(Integer.valueOf(Long.toString(orderService.getOrderCountBySchoolID(schoolID)))+1);
				}
				schoolPraiseService.addSchoolPraise(sp);
			}else{
				if(allUsers.contains(user.getId()))
					schoolPraiseService.addOwnStudent(sp.getId());
				else
					schoolPraiseService.addOtherStudent(sp.getId());
			}
			reponseJson.put("success",true);
			reponseJson.put("msg","点赞成功");
			return reponseJson.toString();
		}catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			reponseJson.put("success",false);
			reponseJson.put("msg","点赞失败！");
			return reponseJson.toString();
		}
	}
	
	

}
