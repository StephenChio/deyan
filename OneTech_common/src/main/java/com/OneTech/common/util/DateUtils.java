package com.OneTech.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @description 时间格式化工具类
 * @date 2018年8月2日
 * @author 吴森荣
 * @email wsr@basehe.com
 * @version v1.0
 */
public class DateUtils {

	/**
	 * 将指定的String日期类型转为Date类型
	 * @param source 
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date strToDate(String source, String pattern) throws ParseException {
		return new SimpleDateFormat(pattern).parse(source);
	}
	
	/**
	 * 将Date类型日期转为指定的String格式
	 * @param source 
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static String dateToStr(Date date , String pattern) throws ParseException {
		return new SimpleDateFormat(pattern).format(date);
	}
	
	/**
	 * 加减时间天数
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date, int days){
		Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, days);//
 
        Date newDate = c.getTime();
        return newDate;
	}
	
	/**
	 * 加减小时数
	 * @param date
	 * @param hours
	 * @return
	 */
	public static Date addHours(Date date, int hours){
		Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, hours);//
 
        Date newDate = c.getTime();
        return newDate;
	}
	
	/**
	 * 加减分钟数
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addMinutes(Date date, int minutes){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, minutes);//
		
		Date newDate = c.getTime();
		return newDate;
	}

	public static void main(String[] args) throws ParseException {

		System.out.println(strToDate("2018-08-02 00:00:00", "yyyy-MM-dd HH:mm:ss"));

		System.out.println(dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));

		System.out.println(addMinutes(new Date(), 5));

		System.out.println(addHours(new Date(), 5));

		System.out.println(addDays(new Date(), 5));

	}



}
