package net.jxquan.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.jxquan.config.QiniuConfig;
import net.jxquan.entity.Image;
import net.jxquan.entity.School;
import net.jxquan.service.ImageService;
import net.jxquan.service.OrderService;
import net.jxquan.service.SchoolService;
import net.jxquan.util.QiniuUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(produces="text/html;charset=UTF-8")
public class IndexController {
	private static final Logger logger=Logger.getLogger(IndexController.class);
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
    
    @RequestMapping(value="/getSchoolByKeyWord",method=RequestMethod.POST)
    public @ResponseBody String getSchoolByMessage(@RequestParam String keyWord,HttpServletRequest request){
		try{
		try {
				request.setCharacterEncoding("utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<School> schools=schoolService.getSchoolByKey(keyWord);
			JSONObject totalJson=new JSONObject();
			JSONArray array=new JSONArray();
			for(School school:schools){
				JSONObject jsonObject=new JSONObject();
				long schoolID=school.getId();
				Image image=imageService.findImageByUrlKey(schoolID,"index");
				String url=null;
				if(image == null){
					url="http://7xjb60.com1.z0.glb.clouddn.com/16_index.jpg";
				}else{
					url=image.getUrl();
				}
				String finalUrl=QiniuUtils.wrapUrl(url, QiniuConfig.MODE, QiniuConfig.INDEX_WIDTH,QiniuConfig.INDEX_HEIGTH);
				jsonObject.put("schoolName",school.getSchoolName());
				jsonObject.put("id",school.getId());
				jsonObject.put("localPrice",school.getLocalPrice());
				jsonObject.put("foreignPrice",school.getForeignPrice());
				jsonObject.put("orders",orderService.getOrderCountBySchoolID(schoolID));
				jsonObject.put("address",school.getAddress());
				jsonObject.put("indexPic",finalUrl);
				jsonObject.put("intro","但愿可以成功一次！");
				array.add(jsonObject);
			}
			totalJson.put("schools", array);
			return totalJson.toString();
		}catch(Exception e){
			JSONObject responseJson =new JSONObject();
			logger.error("出现了萌萌哒错误！",e);
			responseJson.put("msg","后台错误！");
			responseJson.put("success",false);
			return responseJson.toString();
		}
    	}
}
