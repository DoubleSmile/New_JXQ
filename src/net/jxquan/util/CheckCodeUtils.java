package net.jxquan.util;


import java.util.HashMap;
import java.util.Map;

import net.jxquan.config.SmsConfig;

import org.apache.commons.lang.math.RandomUtils;

public class CheckCodeUtils {
	
	/**
	 * 得到随机验证码(100000-999999)
	 */
	public static Integer getCheckCode(){
		Integer checkCode= RandomUtils.nextInt(999999);
		if(checkCode>99999)
			return checkCode;
		else 
			return getCheckCode();
	}
	
	/**
	 * 调用微米的接口向用户发送验证码
	 */
	public static void sendCheckCode(Integer checkCode,String mobile) {

		Map<String, String> para = new HashMap<String, String>();

		/**
		 * 目标手机号码，多个以“,”分隔，一次性调用最多100个号码，示例：139********,138********
		 */
		para.put("mob",mobile);

		/**
		 * 微米账号的接口UID
		 */
		para.put("uid", SmsConfig.UID);

		/**
		 * 微米账号的接口密码
		 */
		para.put("pas", SmsConfig.UID_PASS);

		/**
		 * 接口返回类型：json、xml、txt。默认值为txt
		 */
		para.put("type", "json");

		/**
		 * 短信模板cid，通过微米后台创建，由在线客服审核。必须设置好短信签名，签名规范： <br>
		 * 1、模板内容一定要带签名，签名放在模板内容的最前面；<br>
		 * 2、签名格式：【***】，签名内容为三个汉字以上（包括三个）；<br>
		 * 3、短信内容不允许双签名，即短信内容里只有一个“【】”<br>
		 */
		para.put("cid", SmsConfig.CID);

		/**
		 * 传入模板参数。<br>
		 * <br>
		 * 短信模板示例：<br>
		 * 【微米网】您的验证码是：%P%，%P%分钟内有效。如非您本人操作，可忽略本消息。<br>
		 * <br>
		 * 传入两个参数：<br>
		 * p1：610912<br>
		 * p2：3<br>
		 * 最终发送内容：<br>
		 * 【微米网】您的验证码是：610912，3分钟内有效。如非您本人操作，可忽略本消息。
		 */
		
		para.put("p1",checkCode.toString());
		
		para.put("p2",SmsConfig.TIME);
		
		try {
			System.out.println(
			HttpClientHelper.convertStreamToString(
				HttpClientHelper.post("http://api.weimi.cc/2/sms/send.html", para),"UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
