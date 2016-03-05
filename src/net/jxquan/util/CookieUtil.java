package net.jxquan.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	//通过Cookie的名字得到相应的Cookie
	public static Cookie getCookie(HttpServletRequest request,String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies){
				if (cookie.getName().equals(name))
					return cookie;
			}
			return null;
	}
	//设置相关的Cookie
	public static void setCookie(HttpServletResponse response,String name, String value, int maxAgeInSeconds,String path,String domain) {
		Cookie cookie = new Cookie(name,value);
		cookie.setMaxAge(maxAgeInSeconds);
		cookie.setPath(path);
		//cookie.setDomain(domain);
		response.addCookie(cookie);
	}
}
