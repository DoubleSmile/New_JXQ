package net.jxquan.config;


/**
 * @author luckyyflv
 * @Date  2015/5/27
 */

public class QiniuConfig {
	
	/**
	 * 用于生成Token的秘钥
	 */
	public static final String AK="I33BZs4pvBm5L-3Go3yJS35U-qvz8RKUydsus9yA";

	public static final String SK="_-9329pP5BzWLN4Al4YL6tziirg6UbJIE9qOLJAx";
	
	/**
	 * 用于存储图片的空间名称
	 */
	public static final String BUCKET_NAME="new-jxq";
	/**
	 * bucket空间绑定的URL前缀
	 */
	public static final String domainName="http://7xjkc9.com1.z0.glb.clouddn.com";
	
	/**
	 * 图片的显示模式，详情见http://developer.qiniu.com/docs/v6/api/reference/fop/image/imageview2.html
	 */
	public static final int MODE=1;

	/**
	 * Index界面图片的宽
	 */
	public static final int INDEX_WIDTH=278;
	
	/**
	 * Index界面图片的高
	 */
	public static final int INDEX_HEIGTH=209;
	
	/**
	 * Carousel界面图片的宽
	 */
	public static final int CAROUSEL_WIDTH=480;
	
	/**
	 * Carousel界面图片的高
	 */
	public static final int CAROUSEL_HEIGTH=270;
	/**
	 * 上传界面图片的宽
	 */
	public static final int UPLOAD_WIDTH=500;
	
	/**
	 * 上传界面图片的高
	 */
	public static final int UPLOAD_HEIGTH=270;
}
