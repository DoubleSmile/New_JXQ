package net.jxquan.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.jxquan.entity.Order;
import net.jxquan.entity.School;
import net.jxquan.entity.User;
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
public class OrderController {
	
	private static final Logger logger=Logger.getLogger(OrderController.class);

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
    
	@RequestMapping(value="/user/addOrder",method=RequestMethod.POST)
	public @ResponseBody String addOrder(@RequestBody String requestJson,HttpServletRequest request){
		JSONObject responseJson=new JSONObject();
		try{
			JSONObject requestJSON=JSONObject.fromObject(requestJson);
			User user=(User)request.getSession().getAttribute("user");
			long userID=user.getId();
			long schoolID=Long.valueOf(requestJSON.getString("schoolID"));
			String contactName=requestJSON.getString("contactName");
			String contactMobile=requestJSON.getString("contactMobile");
			int isLocal=Integer.valueOf(requestJSON.getString("isLocal"));
			Order order=new Order();
			order.setCreateTime(new Date());
			order.setIsLocal(isLocal);
			order.setStatus(0);
			order.setSchoolID(schoolID);
			order.setUserID(userID);
			order.setContactName(contactName);
			order.setContactMobile(Long.valueOf(contactMobile));
			order.setOrderNumber(CheckCodeUtils.getCheckCode());
			orderService.addOrder(order);
			responseJson.put("success",true);
			responseJson.put("msg","下单成功");
		}catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			responseJson.put("success",false);
			responseJson.put("msg","下单失败");
		}finally{
			return responseJson.toString();
		}
	}
	
	@RequestMapping(value="/user/getOrders",method=RequestMethod.POST)
	public @ResponseBody String getOrders(HttpServletRequest request){
		JSONObject responseJson=new JSONObject();
		try{
			User user=(User)request.getSession().getAttribute("user");
			List<Order> orders=orderService.getOrderByUserID(user.getId());
			JSONArray array=new JSONArray();
			for(Order o:orders){
				JSONObject json=new JSONObject();
				json.put("orderID", o.getId());
				json.put("orderNumber",o.getOrderNumber());
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        	String date=dateFormat.format(o.getCreateTime()).toString();
				json.put("createTime",date);
				if(o.getStatus() == 0)
					json.put("status","未受理");
				else
					json.put("status","已受理");
				School school=schoolService.getSchoolByID(o.getSchoolID());
				json.put("schoolName",school.getSchoolName());
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
	
	@RequestMapping(value="/user/cancelOrder",method=RequestMethod.POST)
	public @ResponseBody String cancelOrder(@RequestBody String requestJson,HttpServletRequest request){
        JSONObject responseJson=new JSONObject();
		try{
			JSONObject requestJSON=JSONObject.fromObject(requestJson);
			long orderID=Long.valueOf(requestJSON.getString("orderID"));
			orderService.deleteOrder(orderID);
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
	
	@RequestMapping(value="/school/acceptOrder",method=RequestMethod.POST)
	public @ResponseBody String acceptOrder(@RequestBody String requestJson,HttpServletRequest request){
		JSONObject responseJson=new JSONObject();
		try{
			JSONObject requestJSON=JSONObject.fromObject(requestJson);
			String contactName=requestJSON.getString("contactName");
			String contactMobile=requestJSON.getString("contactMobile");
			int orderNumber;
			  try{
				  orderNumber=Integer.valueOf(requestJSON.getString("orderNumber"));
			  }catch(Exception e){
				  JsonUtils.setMsg(responseJson,false,"验证码格式错误！");
				  return responseJson.toString();
			  }
			  try{
				  orderService.acceptOrder(contactName, contactMobile, orderNumber);
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
	
	@RequestMapping(value="/school/getOrders",method=RequestMethod.POST)
	public @ResponseBody String getOrdersForSchool(HttpServletRequest request){
		try{
			School school=(School)request.getSession().getAttribute("school");
	        List<Order> orders=orderService.getOrderForSchool(school.getId());
	        JSONArray array=new JSONArray();
	        for(Order o:orders){
	        	JSONObject temp=new JSONObject();
	        	temp.put("name",o.getContactName());
	        	temp.put("phone",o.getContactMobile());
	        	if(o.getIsLocal()==0)
	        		temp.put("local","本地");
	            else
	        	   temp.put("local","外地");
	        	if(o.getStatus()==0)
	        		temp.put("checked","否");
	        	else
	        		temp.put("checked", "是");
	        	SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        	String date=dateFormat.format(o.getCreateTime()).toString();
	        	temp.put("order_time",date);
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
