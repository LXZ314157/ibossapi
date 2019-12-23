package cn.com.zx.ibossapi.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期处理
 * 
 * @author lvxuezhan
 * @remark 方法概要:
 * 
 *         获取本周一日期:getMondayOFWeek() 获取本周日的日期:getCurrentWeekday()
 *         获取上周一日期:getPreviousWeekday() 获取上周日日期:getPreviousWeekSunday()
 *         获取下周一日期:getNextMonday() 获取下周日日期:getNextSunday()
 *         获取本月第一天日期:getFirstDayOfMonth() 获取本月最后一天日期:getDefaultDay()
 *         获取上月第一天日期:getPreviousMonthFirst() 获取上月最后一天的日期:getPreviousMonthEnd()
 *         获取下月第一天日期:getNextMonthFirst() 获取下月最后一天日期:getNextMonthEnd()
 *         获取本年的第一天日期:getCurrentYearFirst() 获取本年最后一天日期:getCurrentYearEnd()
 *         获取去年的第一天日期:getPreviousYearFirst() 获取去年的最后一天日期:getPreviousYearEnd()
 *         获取明年第一天日期:getNextYearFirst() 获取明年最后一天日期:getNextYearEnd()
 *         获取本季度第一天到最后一天:getThisSeasonTime(int) 是否闰年:isLeapYear(int)
 * 
 * 
 */
public class DateUtil {

	/** 日志记录器 */

	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

	public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";

	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	public static final String YYYYMM = "yyyyMM";

	public static final String YYYYMMDD = "yyyyMMdd";
	
	/** 用来全局控制 上一周，本周，下一周的周数变化 */
	// public static int weeks = 0;
	// public static int MaxDate;// 一月最大天数
	// public static int MaxYear;// 一年最大天数
	/**
	 * 按照指定格式 把指定字符串格式化为时间类型
	 * 
	 * @param str
	 *            需要转换的字符串,如:2009-08-01 12:21:22
	 * @param format
	 *            转换格式,如:yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date string2Date_format(String str, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date d = sdf.parse(str);
			return d;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}


	public static String getMonthAndDay(){
	    Calendar now = Calendar.getInstance();
	    return (now.get(Calendar.MONTH) + 1)+"月"+now.get(Calendar.DAY_OF_MONTH)+"日";
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date now() {
        return new Date();
    }

	public static Date stringDate_format(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat(
				YYYY_MM_DD_HH_MM_SS);
		try {
			Date d = sdf.parse(str);
			return d;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 把字符串格式成時間類型,只支持如下兩種格式轉換:yyyy-MM-dd,yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            传入的时间字符串
	 * @return 转换后的Date类型对象
	 */
	public static Date string2Date_noFormat(String date) {

		Date d = new Date();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		if (date.length() == 19) {
			s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		try {
			d = s.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
	
	public static Date string2DateByYYYYMMDD(String date) {
		Date d = null;
		SimpleDateFormat s = new SimpleDateFormat(YYYYMMDD);
		try {
			d = s.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
	/**
	 * 把指定时间格式化为指定格式的字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式化字符串,如:yyyy-MM-dd HH:mm:ss
	 * @return 字符串的日期
	 */
	public static String date2String_format(Date date, String format) {
		String dateStr = "";
		try {
			Format formatter;
			formatter = new SimpleDateFormat(format);
			dateStr = formatter.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateStr;
	}

	/**
	 * 格式化查询日时间，精确到日期
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Date formatQueryTime(String queryTime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		if (StringUtils.isNotBlank(queryTime)) {
			date = sdf.parse(queryTime);
		}
		return date;
	}

	/**
	 * 按照指定格式获取系统时间
	 * 
	 * @param format
	 *            如:yyyy-MM-dd HH:mm:ss
	 * @return 字符串的日期
	 */
	public static String getSysDate(String format) {
		String dateStr = "";
		try {
			Format formatter;
			Date date = new Date();
			formatter = new SimpleDateFormat(format);
			dateStr = formatter.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateStr;
	}
	/**
	 * 统一格式化日期格式
	 * 
	 *            如:yyyy-MM-dd HH:mm:ss
	 * @return 字符串的日期
	 */
	public static String changeDate(String date){
		String date1="";
		if(date!=null){
			if(date.indexOf("-")!=-1){
				date1=date.replaceAll("-","");
			}else if(date.indexOf(".")!=-1){
				date1=date.replaceAll(".","");
			}else{
				date1=date;
			}
		}
		return date1;
	}
	/**
	 * 判断是否闰年
	 * 
	 * @param year
	 *            年份
	 * @return true 闰年 ,false 平年
	 */
	public static boolean isLeapyear(int year) {
		if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取月份天数
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @return 天数
	 */
	public static int getMonthDays(String year, String month) {
		int yearInt = Integer.parseInt(year);
		int monthInt = Integer.parseInt(month);
		int monthdays = 31;
		switch (monthInt) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12: {
			monthdays = 31;
			break;
		}
		case 2: {
			if (isLeapyear(yearInt)) {
				monthdays = 29;
			} else {
				monthdays = 28;
			}
			break;
		}
		case 4:
		case 6:
		case 9:
		case 11: {
			monthdays = 30;
			break;
		}
		}
		return monthdays;
	}

	/**
	 * 判断某个时间是星期几
	 * 
	 * @param strDate
	 *            需要判断的时间
	 * @return 0 表示是星期天,其他对应
	 */
	public static int getWeekByDate(String strDate) {
		int dayOfWeek = 0;
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			Date date = new Date();
			date = sdf.parse(strDate);
			calendar.setTime(date);
			dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dayOfWeek - 1;
	}


    /**
     * 获取当前时间戳字符串
     * @return
     */
	public static String getTimestamp(){
	    return String.valueOf(new Date().getTime());
    }


	/**
	 * 获得距给定日期countday的字符串格式
	 * 
	 * @param date
	 *            给定日期
	 * @param countday
	 *            天数
	 * @param flag
	 *            true 给定日期之前,false 给定日期之后
	 * @return YYYY-MM-DD 格式字符串日期
	 */
	public static String getDateString(Date date, int countday, boolean flag) {
		String datestr = "";
		// if (flag) {
		// datestr = date2String_format(new Date((new Date()).getTime()
		// - countday * 24 * 60 * 60 * 1000l), "yyyy-MM-dd");
		// } else {
		// datestr = date2String_format(new Date((new Date()).getTime()
		// + countday * 24 * 60 * 60 * 1000l), "yyyy-MM-dd");
		// }
		Date formatedDate = string2Date_format(
				date2String_format(date, YYYY_MM_DD), YYYY_MM_DD);
		if (flag) {
			datestr = date2String_format(new Date(formatedDate.getTime()
					- countday * 24 * 60 * 60 * 1000l), "yyyy-MM-dd");
		} else {
			datestr = date2String_format(new Date(formatedDate.getTime()
					+ countday * 24 * 60 * 60 * 1000l), "yyyy-MM-dd");
		}
		return datestr;
	}

	/**
	 * 获取两个时间之间的相差天数
	 * 
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static Long getDateDifference(Date d1, Date d2)
			throws ParseException {
		Date date1 = string2Date_format(date2String_format(d1, YYYY_MM_DD),
				YYYY_MM_DD);
		Date date2 = string2Date_format(date2String_format(d2, YYYY_MM_DD),
				YYYY_MM_DD);
		// 日期相减得到相差的日期
		long day = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) > 0 ? (date1
				.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000)
				: (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 判断某个日期是否在两个日期之间
	 * 
	 * @param d1
	 *            起始日期
	 * @param d2
	 *            需要判断的日期
	 * @param d3
	 *            结束日期
	 * @return true 在两个日期之间(包括相等) , false 不在两个日期之间
	 */
	public static boolean isIn(Date d1, Date d2, Date d3) {
		boolean result = false;
		if (d2.compareTo(d1) >= 0 && d2.compareTo(d3) <= 0) {
			result = true;
		}
		return result;

	}

	/**
	 * 获得当前日期与上周日相差的天数
	 * 
	 * @return
	 */
	public static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	/**
	 * 获得上周星期一的日期
	 * 
	 * @return 如2009-11-23格式字符串
	 */
	public static String getPreviousWeekday() {
		int weeks = 0;
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获得上周星期日的日期
	 * 
	 * @return 如2009-11-29格式字符串
	 */
	public static String getPreviousWeekSunday() {
		int weeks = 0;
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获得本周一的日期
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getMondayOFWeek() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获得本周星期日的日期
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getCurrentWeekday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获得下周星期一的日期
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getNextMonday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获得下周星期日的日期
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getNextSunday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获取当月第一天
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获取Date 日期 当月第一天
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getFirstDayOfMonthByDate(Date date) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(date);

		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获取Date n月前 日期 当月第一天
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getFirstDayOfMonthByDate(Date date, int n) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(date);
		lastDate.add(Calendar.MONTH, n);
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 计算date日前当月最后一天
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getDateLastDay(Date date) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(date);
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 计算date的 n月 前日前当月最后一天
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getDateLastDay(Date date, int n) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(date);
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1 + n);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 计算当月最后一天
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获取上月第一天
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
		// lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获得上月最后一天
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getPreviousMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获得上月
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getPreviousMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);// 减一个月
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获得前num月
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getPreviousMonthByNum(int num) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, num);// 减一个月
		// lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获得下个月第一天
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getNextMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获得下个月最后一天
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getNextMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 加一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获取今年的总天数
	 * 
	 * @return 如:2010-12-31
	 */
	public static int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}

	/**
	 * 获得本年第一天
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getCurrentYearFirst() {
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}

	/**
	 * 获得本年最后一天
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		return years + "-12-31";
	}

	/**
	 * 获得上年第一天的日期
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getPreviousYearFirst() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		years_value--;
		return years_value + "-1-1";
	}

	/**
	 * 获得上年最后一天
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getPreviousYearEnd() {
		int weeks = 0;
		weeks--;
		int yearPlus = getYearPlus();
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			MaxYear = -MaxYear;
		} else {
			MaxYear = 1 - yearOfNumber;
		}

		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks
				+ (MaxYear - 1));
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		getThisSeasonTime(11);
		return preYearDay;
	}

	/**
	 * 获得本季度第一天和最后一天
	 * 
	 * @param month
	 *            月份
	 * @return 如:2009-10-1;2009-12-31
	 */
	public static String getThisSeasonTime(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + start_month + "-" + start_days
				+ ";" + years_value + "-" + end_month + "-" + end_days;
		return seasonDate;

	}

	/**
	 * 获取某年某月的最后一天
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 最后一天 如:31
	 */
	public static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	/**
	 * 是否闰年
	 * 
	 * @param year
	 *            年
	 * @return true 是,false 否
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	/**
	 * 获得明年第一天
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getNextYearFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		str = sdf.format(lastDate.getTime());
		return str;

	}

	/**
	 * 获得明年第一天
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getNextYearFirstByDate(Date date) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(date);
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		str = sdf.format(lastDate.getTime());
		return str;

	}

	/**
	 * 获得明年最后一天
	 * 
	 * @return 如:2010-12-31
	 */
	public static String getNextYearEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获得昨天的日期
	 * 
	 * @return
	 */
	public static String getYesterday() {

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(c
				.getTime());
		return yesterday;
	}

	/**
	 * 当前时间加上若干个小时
	 * 
	 * @param hour
	 * @return
	 */
	public static Date addHour(int hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, hour);
		return cal.getTime();
	}

	/**
	 * 获取日期间隔秒数量
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDateSpan(Date date1, Date date2) {
		return (date2.getTime() - date1.getTime()) / 1000;

	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int daysBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static long daysBetween2(Date smdate, Date bdate)
			throws ParseException {
		smdate = string2Date_format(date2String_format(smdate, YYYY_MM_DD),
				YYYY_MM_DD);
		bdate = string2Date_format(date2String_format(bdate, YYYY_MM_DD),
				YYYY_MM_DD);
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return between_days;
	}

	/**
	 * 获取当前日期 （返回类型Date）
	 * 
	 *            日期格式
	 * @return Date
	 * @throws ParseException
	 */
	public static Date getNewDay() throws ParseException {
		String nowDate = getSysDate("yyyy-MM-dd");
		return formatQueryTime(nowDate);

	}

	/**
	 * 获取当前日期 （返回类型Date）
	 * 
	 * @param format
	 *            日期格式
	 * @return Date
	 * @throws ParseException
	 */
	public static Date dateFomart(Date date, String format)
			throws ParseException {
		SimpleDateFormat s = new SimpleDateFormat(format);
		date = s.parse(s.format(date));
		return date;

	}

	/**
	 * 当前时间加上若干天
	 * 
	 * @param date
	 *            当前日期
	 * @param day
	 *            要加天数
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}

	/**
	 * 当前时间加上若干天
	 * 
	 * @param date
	 *            当前日期
	 * @param day
	 *            要加天数
	 * @return
	 */
	public static String addDayByString(String date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(string2DateByYYYYMMDD(date));
		cal.add(Calendar.DATE, day);
		return date2String_format(cal.getTime(), YYYYMMDD);
	}

	/**
	 * 获取当前月份（1月=1,2月=3以此类推）
	 * 
	 * @return 返回数字类型的月份
	 */
	public static int getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取上月份（1月=1,2月=3以此类推）
	 * 
	 * @return 返回数字类型的月份
	 */
	public static int getLasttMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * 获取下月份（1月=1,2月=3以此类推）
	 * 
	 * @return 返回数字类型的月份
	 */
	public static int getNexttMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 2;
	}

	/**
	 * 获取最近N月份（1月=1,2月=3以此类推） n=-1 为上月 -2 上上月 1为下月 2下下月
	 * 
	 * @return 返回数字类型的月份
	 */
	public static int getNtMonth(int n) {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1 + n;
	}


}
