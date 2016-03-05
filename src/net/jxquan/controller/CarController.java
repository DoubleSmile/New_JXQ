package net.jxquan.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.jxquan.entity.Appointment;
import net.jxquan.entity.Car;
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
public class CarController {
	
	private static final Logger logger=Logger.getLogger(CarController.class);

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
    
    @RequestMapping(value="/school/getValiableCars",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String getValiableCars(HttpServletRequest request){
		try{
			School school=(School)request.getSession().getAttribute("school");
			List<Car> cars=carService.findAvaiableCars(school.getId());
			JSONArray array=new JSONArray();
			for(Car c:cars){
				JSONObject responseJson=new JSONObject();
               	responseJson.put("carNumber",c.getNumber());
               	responseJson.put("curSize",c.getCurSize());
               	responseJson.put("coachName",coachService.findCoachByID(c.getCoachID()).getName());
               	responseJson.put("schoolName",school.getSchoolName());
//               	responseJson.put("imageUrl", c.getImageUrl());
               	array.add(responseJson);
			}
			return array.toString();
		}catch(Exception e){
			logger.error("出现了萌萌哒错误",e);
			JSONObject json=new JSONObject();
			JsonUtils.setMsg(json,false,"服务器出错");
			return json.toString();
		}
	}
	
	
	
	
	

}
