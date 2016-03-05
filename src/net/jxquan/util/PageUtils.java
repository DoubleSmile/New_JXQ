package net.jxquan.util;

import java.util.List;

import net.jxquan.config.PageConfig;


public class PageUtils {
	/**
	 * 得到总页数
	 *
	 */
	public static int getTotalPage(List list){
		int count=list.size()/PageConfig.PAGE_SIZE;
		int remainder=list.size()%PageConfig.PAGE_SIZE;
		if(count == 0)
			return 1;
		else if(remainder == 0)
			return count;
		else
			return ++count;
	}
}
