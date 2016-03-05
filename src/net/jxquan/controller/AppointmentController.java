package net.jxquan.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.jxquan.entity.Appointment;
import net.jxquan.entity.Order;
import net.jxquan.entity.School;
import net.jxquan.entity.User;
import net.jxquan.service.AppointmentService;
import net.jxquan.service.CarService;
import net.jxquan.service.CoachService;
import net.jxquan.service.OrderService;
import net.jxquan.service.SchoolService;
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
@RequestMapping(produces="text/html;charset=UTF-8")
public class AppointmentController {
	
	private static final Logger logger=Logger.getLogger(AppointmentController.class);

	private OrderService orderService;
    public OrderService getOrderService() {
		return orderService;
	}
    @Autowired
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
    
    private SchoolService schoolService;
    public SchoolService getSchoolService() {
		return schoolService;
	}
    @Autowired
	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}
    
    private AppointmentService appointmentService;
	
	public AppointmentService getAppointmentService() {
		return appointmentService;
	}
    @Autowired
	public void SetAppointmentService(AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}
    
    private CarService carService;
    public CarService getCarService() {
		return carService;
	}
    @Autowired
	public void setCarService(CarService carService) {
		this.carService = carService;
	}
    
    private CoachService coachService;
	
	public CoachService getCoachService() {
		return coachService;
	}
    @Autowired
	public void setCoachService(CoachService coachService) {
		this.coachService = coachService;
	}
    
    @RequestMapping(value="/user/addAppointment",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String addAppointment(@RequestBody String requestJson,HttpServletRequest request){
		JSONObject responseJson=new JSONObject();
		try{
			JSONObject requestJSON=JSONObject.fromObject(requestJson);
//			User user=(User)request.getSession().getAttribute("user");
//			long userID=user.getId();
			long userID=1;
			long schoolID=Long.valueOf(requestJSON.getString("schoolID"));
			long coachID=Long.valueOf(requestJSON.getString("coachID"));
			long carID=Long.valueOf(requestJSON.getString("carID"));
			int timeInterval=Integer.valueOf(requestJSON.getString("timeInterval"));
			String contactName=requestJSON.getString("contactName");
			String contactMobile=requestJSON.getString("contactMobile");
//			String appointTime=requestJSON.getString("appointTime");
//			Date date=SimpleDateFormat.暂时留着
			Appointment appointment=new Appointment();
			appointment.setUserID(userID);
			appointment.setSchoolID(schoolID);
			appointment.setCoachID(coachID);
			appointment.setCarID(carID);
			appointment.setCreateTime(new Date());
			appointment.setAppointTime(new Date());
			appointment.setTimeInterval(timeInterval);
			appointment.setStatus(0);
			appointment.setContactName(contactName);
			appointment.setContactMobile(Long.valueOf(contactMobile));
			appointment.setAppointNumber(CheckCodeUtils.getCheckCode());
			boolean flag=carService.addNumber(carID);
			if(flag){
				appointmentService.addAppointment(appointment);
				responseJson.put("success",true);
				responseJson.put("msg","下单成功");
			}else{
				responseJson.put("success",false);
				responseJson.put("msg","下单失败");
			}
		}catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			responseJson.put("success",false);
			responseJson.put("msg","下单失败");
		}finally{
			return responseJson.toString();
		}
	}
	
	@RequestMapping(value="/user/cancelAppointment",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String cancelAppointment(@RequestBody String requestJson,HttpServletRequest request){
        JSONObject responseJson=new JSONObject();
		try{
			JSONObject requestJSON=JSONObject.fromObject(requestJson);
			long appointmentID=Long.valueOf(requestJSON.getString("appointmentID"));
			appointmentService.deleteAppointment(appointmentID);
			responseJson.put("success",true);
			responseJson.put("msg","取消成功");
			return responseJson.toString();
		}catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			responseJson.put("success", false);
			responseJson.put("msg","取消失败");
			return responseJson.toString();
		}
	}
	
	@RequestMapping(value="/user/getAppointments",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String getAppointmentsForSchool(HttpServletRequest request){
		JSONObject responseJson=new JSONObject();
		try{
//			User user=(User)request.getSession().getAttribute("user");
//			List<Appointment> apps=appointmentService.getAppointmentForUser(user.getId());
			List<Appointment> apps=appointmentService.getAppointmentForUser(1);
			JSONArray array=new JSONArray();
			for(Appointment o:apps){
				JSONObject json=new JSONObject();
				json.put("orderID", o.getId());
				json.put("appointNumber",o.getAppointNumber());
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	        	String date=dateFormat.format(o.getAppointTime()).toString();
				json.put("appointmentTime",date);
				if(o.getStatus() == 0)
					json.put("status","未受理");
				else
					json.put("status","已受理");
				School school=schoolService.getSchoolByID(o.getSchoolID());
				json.put("schoolName",school.getSchoolName());
				json.put("carNumber",carService.findCarByID(o.getCarID()).getNumber());
				if(o.getTimeInterval()==0)
					json.put("timeInterval","上午");
				else if(o.getTimeInterval()==1)
					json.put("timeInterval","下午");
				else
					json.put("timeInterval", "全天");
				array.add(json);
			}
			    responseJson.put("orders",array);
			    return responseJson.toString();
		}catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			responseJson.put("success",false);
			responseJson.put("msg","服务器出错！");
			return responseJson.toString();
		}
	}
	
	
	@RequestMapping(value="/school/acceptAppointment",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String acceptAppointment(@RequestBody String requestJson,HttpServletRequest request){
		JSONObject responseJson=new JSONObject();
		try{
			JSONObject requestJSON=JSONObject.fromObject(requestJson);
			String contactName=requestJSON.getString("contactName");
			String contactMobile=requestJSON.getString("contactMobile");
			int appointNumber;
			  try{
				  appointNumber=Integer.valueOf(requestJSON.getString("appointNumber"));
			  }catch(Exception e){
				  JsonUtils.setMsg(responseJson,false,"验证码格式错误！");
				  return responseJson.toString();
			  }
			  try{
				  appointmentService.acceptAppointment(contactName, contactMobile, appointNumber);
			  }catch(NullPointerException e){
				  JsonUtils.setMsg(responseJson,false,"验证信息有误！");
				  return responseJson.toString();
			  }
			responseJson.put("success",true);
			responseJson.put("msg","成功接受订单！");
			return responseJson.toString();
		}
		catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			responseJson.put("success", false);
			responseJson.put("msg","后台错误！");
			return responseJson.toString();
		}
	}
	
	@RequestMapping(value="/school/getAppointments",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String getAppointments(HttpServletRequest request){
		try{
//			School school=(School)request.getSession().getAttribute("school");
//	        List<Appointment> appointments=appointmentService.getAppointmentForSchool(school.getId());
	        List<Appointment> appointments=appointmentService.getAppointmentForSchool(10);
	        JSONArray array=new JSONArray();
	        for(Appointment o:appointments){
	        	JSONObject temp=new JSONObject();
	        	temp.put("name",o.getContactName());
	        	temp.put("phone",o.getContactMobile());
	        	temp.put("carNumber",carService.findCarByID(o.getCarID()).getNumber());
	        	temp.put("coachName",coachService.findCoachByID(o.getCoachID()).getName());
	        	if(o.getStatus()==0)
	        		temp.put("checked","否");
	        	else
	        		temp.put("checked", "是");
	        	SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	        	String date=dateFormat.format(o.getAppointTime()).toString();
	        	temp.put("appoint_time",date);
	        	if(o.getTimeInterval()==0)
	        		temp.put("timeInterval", "上午");
	        	else if(o.getTimeInterval()==1)
	        		temp.put("timeInterval","下午");
	        	else
	        		temp.put("timeInterval","全天");
	        	array.add(temp);		
        }
	        return array.toString();
		}catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			JSONObject responseJson=new JSONObject();
			responseJson.put("success", false);
			responseJson.put("msg","后台错误！");
			return responseJson.toString();
		}
	}
	
	
	
	
	

}
