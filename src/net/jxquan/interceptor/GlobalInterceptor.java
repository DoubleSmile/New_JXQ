package net.jxquan.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jxquan.dao.UserDAO;
import net.jxquan.entity.User;
import net.jxquan.service.UserService;
import net.jxquan.util.CookieUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class GlobalInterceptor implements HandlerInterceptor{
	private UserService userService; 

    public UserService getUserService() {
		return userService;
	}
    @Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
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
		Cookie cookie=CookieUtil.getCookie(request,"mobile");
		if(cookie==null){
			return true;
		}
		if(cookie!=null && request.getSession().getAttribute("user")==null){
			User user=userService.findByMobile(cookie.getValue());
			if(user!=null){
				request.getSession().setAttribute("user", user);
				request.getSession().setMaxInactiveInterval(3600*24);
			}else{
				cookie.setValue(null);
			}
		}else{
			cookie.setValue(null);
		}
		return true;
        
	}
	

}
