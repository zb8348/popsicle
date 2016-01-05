package com.pfw.popsicle.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 如果涉及到跨时区的服务，直接new Date 会产生时间问题
 * 
 * @author 伍利兵
 */
public class DateUtil {

	private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private final static DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	private final static DateFormat dateFormatmonth = new SimpleDateFormat("yyyy-MM");
	private final static DateFormat timestampFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	protected final static TimeZone CHINESE_TIMEZONE = TimeZone
			.getTimeZone("GMT+8");

	public static Date getChineseTime() {
		return Calendar.getInstance(CHINESE_TIMEZONE).getTime();
	}

	/**
	 * 将时间转换为字符串，格式为：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static synchronized String convertToTimestamp(Date date) {
		return timestampFormat.format(date);
	}

	/**
	 * 将时间转换为字符串，格式为：HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static synchronized String convertToTime(Date date) {
		return timeFormat.format(date);
	}

	/**
	 * 将时间转换为字符串，格式为：yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static synchronized String convertToDate(Date date) {
		return dateFormat.format(date);
	}
	
	public static synchronized Date parseToDate(String date) {
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String parse(String formatText, Date date) {
		DateFormat format = new SimpleDateFormat(formatText);
		return format.format(date);
	}
	public static synchronized String convertToDateMonth(Date date) {
		return dateFormatmonth.format(date);
	}

	public static int getYear(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		return ca.get(Calendar.YEAR);
	}
	
	public static int getMonth(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		return ca.get(Calendar.MONTH)+1;
	}
	
	
	
	/**
	 * 获得指定日期的前一天
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Date getDayBefore(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		return c.getTime();
	}

	/**
	 * 获得指定日期的后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayAfter(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		return c.getTime();
	}
	
	public static synchronized Date parseTotimestampFormat(String date){
		try {
			return timestampFormat.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
}
