package net.jxquan.util;

import java.security.MessageDigest;

/**
 * Created By luckyyflv 5/24/2015
 * 运用DB5加密密码
 */
public class DB5Utils {

	    /**
	     * 把字节数组转成16进位制数
	     *
	     * @param bytes
	     * @return
	     */
	    public static String bytesToHex(byte[] bytes) {
	        StringBuffer md5str = new StringBuffer();
	        //把数组每一字节换成16进制连成md5字符串
	        int digital;
	        for (int i = 0; i < bytes.length; i++) {
	            digital = bytes[i];
	            if (digital < 0) {
	                digital += 256;
	            }
	            if (digital < 16) {
	                md5str.append("0");
	            }
	            md5str.append(Integer.toHexString(digital));
	        }
	        return md5str.toString().toUpperCase();
	    }

	    /**
	     * 把字节数组转换成md5
	     *
	     * @param input
	     * @return
	     */
	    public static String bytesToMD5(byte[] input) {
	        String md5str = null;
	        try {
	            //创建一个提供信息摘要算法的对象，初始化为md5算法对象
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            //计算后获得字节数组
	            byte[] buff = md.digest(input);
	            //把数组每一字节换成16进制连成md5字符串
	            md5str = bytesToHex(buff);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return md5str;
	    }

	    /**
	     * 把字符串转换成md5
	     *先将字符串数组变为Byte数组，然后将Byte数组通过DB5转换为相应的对应字符串
	     * @param str
	     * @return
	     */
	    public static String strToMD5(String str) {
	        byte[] input = str.getBytes();
	        return bytesToMD5(input);
	    }



}
