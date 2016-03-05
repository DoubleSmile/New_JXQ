package net.jxquan.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jxquan.dao.UserDAO;
import net.jxquan.entity.School;
import net.jxquan.entity.User;
import net.jxquan.service.SchoolService;
import net.jxquan.service.UserService;
import net.jxquan.util.CookieUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class SchoolInterceptor implements HandlerInterceptor{
    private SchoolService schoolService; 

    public SchoolService getSchoolService() {
		return schoolService;
	}
    @Autowired
	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
    @Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception{
		Cookie cookie=CookieUtil.getCookie(request,"schoolCookie");
		if(cookie==null){
			return true;
		}
		if(cookie!=null && request.getSession().getAttribute("school")==null){
			School school=schoolService.getSchoolByCookie(cookie.getValue());
			if(school!=null){
				request.getSession().setAttribute("school", school);
				request.getSession().setMaxInactiveInterval(60*15);
			}else{
				cookie.setValue(null);
			}
		}else{
			cookie.setValue(null);
		}
		return true;
        
	}
	

}
