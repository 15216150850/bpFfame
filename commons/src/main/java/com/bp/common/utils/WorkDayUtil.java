package com.bp.common.utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 工作日工具类
 * @author yanjisheng
 *
 */
public class WorkDayUtil {
	
	//以下内容请每年更新一次
	//节假日
	private static String[] holidays = {
			"20200101", //元旦
			"20200124","20200127","20200128","20200129","20200130", //春节
			"20200406", //清明
			"20200501","20200504","20200505", //五一
			"20200625","20200626", //端午
			"20201001","20201002","20201005","20201006","20201007","20201008" //中秋&国庆
	};
	//补班工作日
	private static String[] workdays = {
			"20200119","20200201", //春节
			"20200426","20200509", //五一
			"20200628", //端午
			"20200927","20201010" //中秋&国庆
	};
	//以上内容请每年更新一次
	
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * 判断给定的日期是否是工作日
	 * @param day
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public synchronized static boolean isWorkday(Date day) {
		if(Arrays.asList(holidays).contains(format.format(day))) {
			return false;
		} else if(Arrays.asList(workdays).contains(format.format(day))) {
			return true;
		} else {
			return !(day.getDay() == 0 || day.getDay() == 6);
		}
	}
	
	/**
	 * 今天后的第几个工作日是哪一天
	 * @param today
	 * @param workdaysLater
	 * @return
	 */
	public static Date laterWorkdays(Date today, int workdaysLater) {
		Date laterDay = null;
		int t = 0;
		for(int i = 1; i <= workdaysLater; i++) {
			laterDay = new Date(System.currentTimeMillis() + 86400000l * (i + t));
			if(!isWorkday(laterDay)) {
				t++;
				i--;
			}
		}
		return laterDay;
	}
	
//	public static void main(String[] args) {
//		System.out.println(format.format(laterWorkdays(new Date(), 10)));
//	}
}
