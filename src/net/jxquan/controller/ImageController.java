package net.jxquan.controller;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.jxquan.config.ImageConfig;
import net.jxquan.config.QiniuConfig;
import net.jxquan.entity.Image;
import net.jxquan.entity.School;
import net.jxquan.service.ImageService;
import net.jxquan.service.SchoolService;
import net.jxquan.util.JsonUtils;
import net.jxquan.util.QiniuUtils;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author luckyyflv
 * @Date  2015/5/26
 */

@Controller
@RequestMapping(value="/image",produces="text/html;charset=UTF-8")
public class ImageController {
	private static final Logger logger=Logger.getLogger(ImageController.class);
	private ImageService imageService;
	
	public ImageService getImageService() {
		return imageService;
	}
    @Autowired
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
    
    private SchoolService schoolService;

	public SchoolService getSchoolService() {
		return schoolService;
	}
	@Autowired
	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public @ResponseBody String upload(HttpServletRequest request){
		JSONObject responseJson=new JSONObject();
		try{
			// 转型为MultipartHttpRequest
	        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	        // 根据前台的name名称得到上传的文件
	        MultipartFile file = multipartRequest.getFile("file");
	        // 获得文件名：
	        String realFileName = file.getOriginalFilename();
	        String imageType=realFileName.split("\\.")[0];
	        // 获取路径
	        String destination=ImageConfig.PATH;
	        
	        //拼出图片的名字
	        StringBuilder key=new StringBuilder();
	        System.out.println(request.getParameter("schoolID")+"---"+request.getParameter("location"));
	        Long schoolID=Long.valueOf(request.getParameter("schoolID"));
	        String location=request.getParameter("location");
	        key.append(schoolID);
	        key.append("_");
	        key.append(location);
	        
	        // 创建文件
	        File uploadFile = new File(destination + key+"."+imageType);
	        
			StringBuilder url=new StringBuilder();
			url.append(ImageConfig.DOMAIN);
			url.append(key);
			url.append(".");
			url.append(imageType);
			if(imageService.findByKey(key.toString())==null){
				imageService.addImage(key.toString(), url.toString());
			}else{
				Image image=imageService.findByKey(key.toString());
				image.setUrl(url.toString());
				imageService.updateImage(image);
			}
			try {
				FileCopyUtils.copy(file.getBytes(), uploadFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			responseJson.put("msg","上传成功");
			responseJson.put("success",true);
			School school=schoolService.getSchoolByID(schoolID);
			request.getServletContext().setAttribute("school",school);
			JsonUtils.setMsg(responseJson,true,"Upload Success");
			return responseJson.toString();
		}catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			JsonUtils.setMsg(responseJson,false,"Upload Filed");
			return responseJson.toString();
		}
	}
	
	@RequestMapping(value="/testUpload",method=RequestMethod.POST)
	public @ResponseBody String testUpload(HttpServletRequest request){

        // 转型为MultipartHttpRequest
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 根据前台的name名称得到上传的文件
        MultipartFile file = multipartRequest.getFile("file");
        // 获得文件名：
        String realFileName = file.getOriginalFilename();
        // 获取路径
        String destination=ImageConfig.PATH;
        // 创建文件
        File uploadFile = new File(destination + realFileName);
        try {
			FileCopyUtils.copy(file.getBytes(), uploadFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "UPLOAD SUCCESS";
//        request.setAttribute("files", loadFiles(request));
	}
	
	/*@RequestMapping(value="/getToken",method=RequestMethod.GET)
	public @ResponseBody String getToken(){
		//具体的callbackUrl要根据服务器IP来确定
		JSONObject responseJson=new JSONObject();
		try{
			String callbackUrl="http://120.24.91.20:8080/New_JXQ/image/upload.do";
//			String callbackUrl="http://localhost:8080/New_JXQ/image/upload.do";
			String callbackBody="name=$(x:name)&location=$(x:location)&schoolID=$(x:schoolID)";
			String returnUrl="http://120.24.91.20:8080/New_JXQ/#/index/schoolPublish";
//			String returnUrl="http://localhost:8080/New_JXQ/#/index/schoolPublish";
			String token=QiniuUtils.getToken(callbackUrl,callbackBody,returnUrl);
			responseJson.put("token",token);
	        return responseJson.toString();
		}catch(Exception e){
			logger.error("出现了萌萌哒错误", e);
			responseJson.put("msg","上传失败");
			responseJson.put("success",false);
			return responseJson.toString(); 
		}
	}*/

}
