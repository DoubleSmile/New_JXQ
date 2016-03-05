package net.jxquan.controller;

import net.jxquan.service.ImageService;
import net.jxquan.service.UserService;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {
	
     private ImageService imageService;
	
	public ImageService getImageService() {
		return imageService;
	}
    @Autowired
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
    
private UserService userService;
	
	public UserService getUserService() {
		return userService;
	}
    @Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	@RequestMapping(value="/testJson",method=RequestMethod.POST)
	public @ResponseBody String testJson(@RequestBody String requestJson){
		JSONObject requestJSON=JSONObject.fromObject(requestJson);
		int tempInt=Integer.valueOf(requestJSON.getString("name"));
		System.out.println(tempInt);
		JSONObject responseJson=new JSONObject();
		responseJson.put("Test", "Success");
		return responseJson.toString();
	}
	
	@RequestMapping(value="/test",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String test(){
		JSONObject json=new JSONObject();
		json.put("Demo","大妹妹");
		return json.toString();
		
	}
	
	@RequestMapping(value="/testQiniu",method=RequestMethod.GET)
	public ModelAndView testQiniu(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("test");
		return mv;
	}
	
	@RequestMapping(value="/testUpload",method=RequestMethod.GET)
	public ModelAndView testUpload(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("index");
//		imageService.addImage("df","sddsfsdfd");
//		imageService.deleteImageByID(7);
		return mv;
	}
	
	@RequestMapping(value="/testUser",method=RequestMethod.GET)
	public ModelAndView testUser(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("index");
		userService.register("12312312312", "123123", "234432");
		return mv;
	}

}
