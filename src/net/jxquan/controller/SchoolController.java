package net.jxquan.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jxquan.config.ImageConfig;
import net.jxquan.entity.Appointment;
import net.jxquan.entity.Car;
import net.jxquan.entity.Image;
import net.jxquan.entity.Order;
import net.jxquan.entity.School;
import net.jxquan.entity.SchoolPraise;
import net.jxquan.service.AppointmentService;
import net.jxquan.service.CarService;
import net.jxquan.service.CoachService;
import net.jxquan.service.ImageService;
import net.jxquan.service.OrderService;
import net.jxquan.service.SchoolPraiseService;
import net.jxquan.service.SchoolService;
import net.jxquan.service.ServeRelationService;
import net.jxquan.util.CookieUtil;
import net.jxquan.util.JsonUtils;
import net.jxquan.util.PageUtils;
import net.jxquan.util.QiniuUtils;
import net.jxquan.util.TopComparator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/school",produces="text/html;charset=UTF-8")
public class SchoolController {
	private static final Logger logger=Logger.getLogger(SchoolController.class);
	private ServeRelationService serveRelationService;
	
	public ServeRelationService getServeRelationService() {
		return serveRelationService;
	}
    @Autowired
	public void SeterveRelationService(ServeRelationService serveRelationService) {
		this.serveRelationService = serveRelationService;
	}
    
    private SchoolPraiseService schoolPraiseService;
	
	public SchoolPraiseService getSchoolPraiseService() {
		return schoolPraiseService;
	}
    @Autowired
	public void SetSchoolPraiseService(SchoolPraiseService schoolPraiseService) {
		this.schoolPraiseService = schoolPraiseService;
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
	private SchoolService schoolService;

	public SchoolService getSchoolService() {
		return schoolService;
	}
	@Autowired
	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}
	private ImageService imageService;
		
	public ImageService getImageService() {
		return imageService;
	}
    @Autowired
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
    
    private CoachService coachService;
	
	public CoachService getCoachService() {
		return coachService;
	}
    @Autowired
	public void setCoachService(CoachService coachService) {
		this.coachService = coachService;
	}
    
    private CarService carService;
    public CarService getCarService() {
		return carService;
	}
    @Autowired
	public void setCarService(CarService carService) {
		this.carService = carService;
	}
    
     /**
      * 用来获得驾校列表
      */
	@RequestMapping(value="/getSchoolList",method=RequestMethod.POST)
	public @ResponseBody String getSchoolList(){
		try{
			List<School> schools=schoolService.getAllIDAndName();
			JSONArray array=new JSONArray();
			for(School school:schools){
				JSONObject tempJson=new JSONObject();
				tempJson.put("schoolName", school.getSchoolName());
				tempJson.put("id", school.getId());
				array.add(tempJson);
				}
			return array.toString();
		}
		catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			JSONObject responseJson=new JSONObject();
			responseJson.put("success",false);
			responseJson.put("msg","后台错误！");
			return responseJson.toString();
		}
		
	}
	
	/**
     * 用来获得首页驾校信息
     */
	@RequestMapping(value="/getSchoolMessage",method=RequestMethod.POST)
	public @ResponseBody String getSchoolMessage(@RequestBody String requestJson){
		try{
			System.out.println(requestJson);
			JSONObject requestJSON=JSONObject.fromObject(requestJson);
			List<School> schools=schoolService.getSchoolsForPage(Integer.valueOf(requestJSON.getString("curPage")),requestJSON.getString("location"));
			System.out.println(schools.size());
			JSONObject totalJson=new JSONObject();
			JSONArray array=new JSONArray();
			for(School school:schools){
				JSONObject jsonObject=new JSONObject();
				long schoolID=school.getId();
				Image image=imageService.findImageByUrlKey(schoolID,"index");
				String url=null;
				if(image == null){
					url=ImageConfig.DOMAIN+"12_front.jpg";
				}else 
					url=image.getUrl();
					jsonObject.put("schoolName",school.getSchoolName());
					jsonObject.put("id",school.getId());
					jsonObject.put("localPrice",school.getLocalPrice());
					jsonObject.put("foreignPrice",school.getForeignPrice());
					jsonObject.put("address",school.getAddress());
					jsonObject.put("orders",orderService.getOrderCountBySchoolID(schoolID));
					jsonObject.put("indexPic",url);
					jsonObject.put("intro","赶快来预约吧骚年！");
					jsonObject.put("discount",school.getDiscount());
					array.add(jsonObject);
			}
			totalJson.put("schools", array);
			System.out.println(array.toString());
			totalJson.put("totalPage",PageUtils.getTotalPage(schoolService.getAllIDAndName()));
			return totalJson.toString();
		}catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			JSONObject responseJson =new JSONObject();
			responseJson.put("success", false);
			responseJson.put("msg","后台错误！");
			return responseJson.toString();
		}
	}
	
	/**
     * 用来获得驾校相关信息(显示驾校主页时候使用)
     */
	@RequestMapping(value="/getSchoolByID",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String getSchoolByID(@RequestBody String requestJson){
		JSONObject responseJson=new JSONObject();
		try{
			JSONObject requestJSON=JSONObject.fromObject(requestJson);
			Long schoolID=Long.valueOf(requestJSON.getString("schoolID"));
			School school=schoolService.getSchoolByID(schoolID);
			responseJson.put("id", school.getId());
			responseJson.put("name", school.getSchoolName());
			responseJson.put("localPrice", school.getLocalPrice());
			responseJson.put("foreignPrice", school.getForeignPrice());
			responseJson.put("orders",orderService.getOrderCountBySchoolID(school.getId()));
			responseJson.put("phone", school.getMobile());
			responseJson.put("locate", school.getAddress());
//			JSONObject collegeJson=new JSONObject();
//			collegeJson.put("colleges", serveRelationService.getCollegeBySchoolID(schoolID));
//			responseJson.put("serveSchools",collegeJson.toString());
			responseJson.put("mapUrl","http://j.map.baidu.com/6gTq3");
			JSONArray colleges=new JSONArray();
			for(String s:serveRelationService.getCollegeBySchoolID(schoolID)){
				JSONObject object=new JSONObject();
				object.put("college",s);
				colleges.add(object);
			}
			responseJson.put("serviceSchool",colleges.toString());
			responseJson.put("intro","约车者，期末不挂科也！");
			responseJson.put("durationOne",school.getDurationOne());
			responseJson.put("durationTwo",school.getDurationTwo());
			responseJson.put("durationThree",school.getDurationThree());
			responseJson.put("durationFour",school.getDurationFour());
			responseJson.put("passRateOne",school.getPassRateOne());
			responseJson.put("passRateTwo",school.getPassRateTwo());
			responseJson.put("passRateThree",school.getPassRateThree());
			responseJson.put("passRateFour",school.getPassRateFour());
			responseJson.put("teacherResource", school.getTeacherResource());
			responseJson.put("hardwareResource",school.getHardwareResource());
			responseJson.put("discount", school.getDiscount());
			List<String> urls=new ArrayList<String>();
			List<Image> images=imageService.findImagesByUrlKey(Long.valueOf(schoolID),"carousel");
			for(Image i:images){
				urls.add(i.getUrl());
			}
			responseJson.put("success", true);
			responseJson.put("carousel",urls);
			return responseJson.toString();
		}catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			responseJson.put("msg","后台错误！");
			return responseJson.toString();
		}
	}
	
	
	
	
	
	@RequestMapping(value="/login",method={RequestMethod.POST,RequestMethod.OPTIONS})
	public @ResponseBody String login(@RequestBody String requestJson,HttpServletRequest request,HttpServletResponse response){
		JSONObject responseJson=new JSONObject();
		try{
			JSONObject requestJSON=JSONObject.fromObject(requestJson);
			if(request.getMethod().equals("OPTIONS"))
				return null;
			String mobile=requestJSON.getString("phone");
			String password=requestJSON.getString("password");
			responseJson.put("type","school");
			responseJson.put("phone",mobile);
			if(schoolService.findByMobile(mobile) == null){
				responseJson.put("success", false);
				responseJson.put("msg","您输入的电话号码没有注册！");
				return responseJson.toString();
			}
			else if(schoolService.findSchoolByMobileAndPassword(mobile,password) == null){
				responseJson.put("success", false);
				responseJson.put("msg","您输入的的密码有误");
				return responseJson.toString();
			}
			else {
				School school=schoolService.findByMobile(mobile);
				responseJson.put("name",school.getSchoolName());
				responseJson.put("schoolID",school.getId());
				CookieUtil.setCookie(response, "schoolCookie",school.getCookie(), 3600*24*7, "/","127.0.0.1");
				request.getSession().setAttribute("school",school);
				request.getSession().setMaxInactiveInterval(60*15);
				responseJson.put("success",true);
				return responseJson.toString();
			}
		}catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			responseJson.put("success", false);
			responseJson.put("msg","登陆失败");			
            return responseJson.toString();
 		}
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public @ResponseBody String register(@RequestBody String requestJson,HttpServletRequest request){
		JSONObject responseJson=new JSONObject();
		try{
			JSONObject requestJSON=JSONObject.fromObject(requestJson);
			String mobile=requestJSON.getString("mobile");
			String password=requestJSON.getString("password");
			String schoolName=requestJSON.getString("schoolName");
			if(schoolService.findByMobile(mobile) != null){
				responseJson.put("success", false);
				responseJson.put("msg","您输入的电话号码已经被注册！");
				return responseJson.toString();
			}
			schoolService.register(mobile, password, schoolName);
			responseJson.put("success",true);
			return responseJson.toString();
		}catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			responseJson.put("success", false);
			responseJson.put("msg","后台错误！");
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
				if(c.getName().equals("schoolCookie")){
					CookieUtil.setCookie(response,"schoolCookie",null,1, "/","localhost");
				}
			}
			  request.getSession().removeAttribute("schoolCookie");
			  responseJson.put("success",true);
			  return responseJson.toString();
			}catch(Exception e){
				logger.error("出现了萌萌哒错误", e);
				responseJson.put("success", false);
				responseJson.put("msg","后台错误！");
				return responseJson.toString();
			}
			
		}
		
		@RequestMapping(value="/updateSchool",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody String updateSchool(@RequestBody String requestJson,HttpServletRequest request){
			JSONObject responseJson=new JSONObject();
			try {
				request.setCharacterEncoding("utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try{
				JSONObject requestJSON=JSONObject.fromObject(requestJson);
				School school=new School();
				school.setId(Long.valueOf(requestJSON.getString("schoolID")));
				String mail=requestJSON.getString("mail");
				school.setMail(mail);
				String telephone=requestJSON.getString("plane");
				school.setTelephone(Long.valueOf(telephone));
				String address=requestJSON.getString("address");
				school.setAddress(address);
				String otherPrice=requestJSON.getString("otherPrice");
				school.setOtherPrice(Double.valueOf(otherPrice));
				String localPrice=requestJSON.getString("localPrice");
				school.setLocalPrice(Double.valueOf(localPrice));
				String foreignPrice=requestJSON.getString("foreignPrice");
				school.setForeignPrice(Double.valueOf(foreignPrice));
				String hardwareResource=requestJSON.getString("hardwareResource");
				school.setHardwareResource(hardwareResource);
				String teacherResource=requestJSON.getString("teacherResource");
				school.setTeacherResource(teacherResource);
				String studentPerCar=requestJSON.getString("studentPerCar");
				school.setStudentPerCar(Integer.valueOf(studentPerCar));
				String durationOne=requestJSON.getString("durationOne");
				school.setDurationOne(Integer.valueOf(durationOne));
				String durationTwo=requestJSON.getString("durationTwo");
				school.setDurationTwo(Integer.valueOf(durationTwo));
				String durationThree=requestJSON.getString("durationThree");
				school.setDurationThree(Integer.valueOf(durationThree));
				String durationFour=requestJSON.getString("durationFour");
				school.setDurationFour(Integer.valueOf(durationFour));
				String passRateOne=requestJSON.getString("passRateOne");
				school.setPassRateOne(Double.valueOf(passRateOne));
				String passRateTwo=requestJSON.getString("passRateTwo");
				school.setPassRateTwo(Double.valueOf(passRateTwo));
				String passRateThree=requestJSON.getString("passRateThree");
				school.setPassRateThree(Double.valueOf(passRateThree));
				String passRateFour=requestJSON.getString("passRateFour");
				school.setPassRateFour(Double.valueOf(passRateFour));
				schoolService.updateSchool(school);
				School curSchool=schoolService.getSchoolByID(school.getId());
				request.getSession().setAttribute("school",curSchool);
				responseJson.put("success",true);
				return responseJson.toString();
			}catch(Exception e){
				logger.error("出现了萌萌哒错误", e);
				responseJson.put("success", false);
				responseJson.put("msg","后台错误！");
				return responseJson.toString();
			}
		}
		
		@RequestMapping(value="/getSchoolDetail",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody String getSchoolDetail(@RequestBody String requestJson,HttpServletRequest request){
			JSONObject responseJson=new JSONObject();
			try{
				School school=(School)request.getSession().getAttribute("school");
				if(school == null){
					school = (School)request.getServletContext().getAttribute("school");
				}
				responseJson.put("leaderName",null);
				responseJson.put("plane",school.getTelephone());
				responseJson.put("mail",school.getMail());
				responseJson.put("address",school.getAddress());
				responseJson.put("localPrice",school.getLocalPrice());
				responseJson.put("foreignPrice",school.getForeignPrice());
				responseJson.put("otherPrice",school.getOtherPrice());
				responseJson.put("studentPerCar",school.getStudentPerCar());
				responseJson.put("passRateOne",school.getPassRateOne());
				responseJson.put("passRateTwo",school.getPassRateTwo());
				responseJson.put("passRateThree",school.getPassRateThree());
				responseJson.put("passRateFour",school.getPassRateFour());
				responseJson.put("durationOne",school.getDurationOne());
				responseJson.put("durationTwo",school.getDurationTwo());
				responseJson.put("durationThree",school.getDurationThree());
				responseJson.put("durationFour",school.getDurationFour());
				responseJson.put("teacherResource",school.getTeacherResource());
				responseJson.put("hardwareResource",school.getHardwareResource());
				Image indexImage=imageService.findImageByUrlKey(school.getId(),"index");
				if(indexImage!=null)
				    responseJson.put("img_index",indexImage.getUrl());
				else
					responseJson.put("img_index",null);
				Image licenseImage=imageService.findImageByUrlKey(school.getId(),"license");
				if(licenseImage!=null)
				    responseJson.put("img_license",licenseImage.getUrl());
				else
					responseJson.put("img_license",null);
				
				Image IDcardImage=imageService.findImageByUrlKey(school.getId(),"IDcard");
				if(IDcardImage!=null)
					responseJson.put("img_IDcard",IDcardImage.getUrl());
				else
					responseJson.put("img_IDcard",null);
				
				List<Image> carousels=imageService.findImagesByUrlKey(school.getId(),"carousel");
				if(carousels==null)
					responseJson.put("img_carousel",null);
				else{
					List<String> lists=new ArrayList<String>();
					for(Image i:carousels){
						lists.add(i.getUrl());
					}
					responseJson.put("img_carousel",lists);
				}	
				responseJson.put("location",school.getLocation());
				JsonUtils.setMsg(responseJson,true,"成功");
				return responseJson.toString();
			}catch(Exception e){
				logger.error("出现了萌萌哒错误！",e);
				JsonUtils.setMsg(responseJson,false,"获取信息失败");
				return responseJson.toString();
			}
		}
		
		
		@RequestMapping(value="/getTop5AroundCollege",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody String getTop5(@RequestBody String requestJson){
			JSONObject responseJson=new JSONObject();
			try{
				JSONObject requestJSON=JSONObject.fromObject(requestJson);
				String college=requestJSON.getString("college");
				List<Long> allSchoolID=serveRelationService.getSchoolIDByCollege(college);
				Set<JSONObject> messageSet=new TreeSet<JSONObject>(new TopComparator());
				for(Long schoolID:allSchoolID){
					JSONObject countMessage=new JSONObject();
					countMessage.put("schoolID",schoolID);
					long totalOrders=orderService.getAllOrderCount();
					long schoolOrders=orderService.getOrderCountBySchoolID(schoolID);
					countMessage.put("totalOrders",totalOrders);
					countMessage.put("schoolOrders",schoolOrders);
					SchoolPraise sp=schoolPraiseService.getSchoolPraiseBySchoolID(schoolID);
					if(sp==null){
						sp=new SchoolPraise();
						sp.setTotalStudent(Integer.valueOf(Long.toString(schoolOrders)));
						sp.setOtherStudent(0);
						sp.setOwnStudent(0);
						sp.setPraiseAmount(0);
						sp.setPariseRate(0.00);
						sp.setSchoolID(schoolID);
						schoolPraiseService.addSchoolPraise(sp);
					}
					countMessage.put("ownStudent",sp.getOwnStudent());
					countMessage.put("otherStudent",sp.getOtherStudent());
					countMessage.put("totalStudent",sp.getTotalStudent());
					messageSet.add(countMessage);
				}
				List<Long> finalSort=new ArrayList<Long>();
				for(JSONObject target:messageSet){
					finalSort.add(target.getLong("schoolID"));
				}
				JSONArray schools=new JSONArray();
				for(Long schoolID:finalSort){
					School school=schoolService.getSchoolByID(schoolID);
					JSONObject jsonObject=new JSONObject();
					Image image=imageService.findImageByUrlKey(schoolID,"index");
					String url=null;
					if(image == null){
						url=ImageConfig.DOMAIN+"12_front.jpg";
					}else 
						url=image.getUrl();
						jsonObject.put("schoolName",school.getSchoolName());
						jsonObject.put("id",school.getId());
						jsonObject.put("localPrice",school.getLocalPrice());
						jsonObject.put("foreignPrice",school.getForeignPrice());
						jsonObject.put("address",school.getAddress());
						jsonObject.put("orders",orderService.getOrderCountBySchoolID(schoolID));
						jsonObject.put("indexPic",url);
						jsonObject.put("intro","赶快来预约吧骚年！");
						jsonObject.put("discount",school.getDiscount());
						schools.add(jsonObject);
				}
				responseJson.put("schools",schools);
				responseJson.put("success", false);
				return responseJson.toString();
			}catch(Exception e){
				logger.error("出现了萌萌哒错误！",e);
				JsonUtils.setMsg(responseJson,false,"获取信息失败");
				return responseJson.toString();
			}
			
		}
		
		@RequestMapping(value="/getTop5AroundLocation",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody String getTop5AroundLocation(@RequestBody String requestJson){
			JSONObject responseJson=new JSONObject();
			try{
				JSONObject requestJSON=JSONObject.fromObject(requestJson);
				String location=requestJSON.getString("location");
				List<Long> allSchoolID=schoolService.findSchoolByLocation(location);
				System.out.println("List的大小为："+allSchoolID.size());
				Set<JSONObject> messageSet=new TreeSet<JSONObject>(new TopComparator());
				for(Long schoolID:allSchoolID){
					JSONObject countMessage=new JSONObject();
					countMessage.put("schoolID",schoolID);
					countMessage.put("totalOrders",Integer.valueOf(Long.valueOf(orderService.getAllOrderCount()).toString()));
					long schoolOrders=orderService.getOrderCountBySchoolID(schoolID);
					countMessage.put("schoolOrders",Integer.valueOf(Long.valueOf(schoolOrders).toString()));
					SchoolPraise sp=schoolPraiseService.getSchoolPraiseBySchoolID(schoolID);
					if(sp==null){
						sp=new SchoolPraise();
						sp.setTotalStudent(Integer.valueOf(Long.toString(schoolOrders)));
						sp.setOtherStudent(0);
						sp.setOwnStudent(0);
						sp.setPraiseAmount(0);
						sp.setPariseRate(00.00);
						sp.setSchoolID(schoolID);
						schoolPraiseService.addSchoolPraise(sp);
					}
					countMessage.put("ownStudent",sp.getOwnStudent());
					countMessage.put("otherStudent",sp.getOtherStudent());
					countMessage.put("totalStudent",sp.getTotalStudent());
					messageSet.add(countMessage);
				}
				List<Long> finalSort=new ArrayList<Long>();
				for(JSONObject target:messageSet){
					finalSort.add(target.getLong("schoolID"));
				}
				JSONArray schools=new JSONArray();
				for(Long schoolID:finalSort){
					School school=schoolService.getSchoolByID(schoolID);
					JSONObject jsonObject=new JSONObject();
					Image image=imageService.findImageByUrlKey(schoolID,"index");
					String url=null;
					if(image == null){
						url=ImageConfig.DOMAIN+"12_front.jpg";
					}else 
						url=image.getUrl();
						jsonObject.put("schoolName",school.getSchoolName());
						jsonObject.put("id",school.getId());
						jsonObject.put("localPrice",school.getLocalPrice());
						jsonObject.put("foreignPrice",school.getForeignPrice());
						jsonObject.put("address",school.getAddress());
						jsonObject.put("orders",orderService.getOrderCountBySchoolID(schoolID));
						jsonObject.put("indexPic",url);
						jsonObject.put("intro","赶快来预约吧骚年！");
						jsonObject.put("discount",school.getDiscount());
						schools.add(jsonObject);
				}
				responseJson.put("schools",schools);
				responseJson.put("success", false);
				return responseJson.toString();
			}catch(Exception e){
				logger.error("出现了萌萌哒错误！",e);
				JsonUtils.setMsg(responseJson,false,"获取信息失败");
				return responseJson.toString();
			}
			
		}
		
		
		
		
		
		
}
