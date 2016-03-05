package net.jxquan.util;

import net.jxquan.config.QiniuConfig;

import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

public class QiniuUtils {
	  public static UploadManager uploadManager = new UploadManager();
	  public static Auth auth = Auth.create(QiniuConfig.AK, QiniuConfig.SK);
	
	
	/**
	 * 指定上传策略
	 */
	public static  String getToken(String callbackUrl,String callbackBody,String returnUrl){
	      return auth.uploadToken(QiniuConfig.BUCKET_NAME,null, 3600, new StringMap()
	           .put("callbackUrl", callbackUrl)
	           .put("callbackBody", callbackBody)
	           .put("returnUrl",returnUrl));
	  }
	
	
	/**
	 * 根据传入的图片大小以及图片的处理模式包装图片
	 */
	public static String wrapUrl(String source,int mode,int width,int height){
		StringBuilder sb=new StringBuilder(source);
		sb.append("?imageView2/");
		sb.append(mode);
		sb.append("/w/");
		sb.append(width);
		sb.append("/h/");
		sb.append(height);
		source=sb.toString();
		return source;
	}
	  
	  

}
