package com.platform.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * 判断是否为闰年
	 * 判定公历闰年遵循的一般规律为: 四年一闰,百年不闰,四百年再闰.
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public static boolean isLeapYear(int year)throws Exception{
		if((year % 4) != 0){
			return false;
		}
		if(((year % 100) == 0) && ((year % 400) != 0)){
			return false;
		}
		return true;
	}
	
	/**
	 * 计算两个日期之间的天数
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int getDaysBetween(Date beginDate, Date endDate)throws Exception{
		return new Long((endDate.getTime() - beginDate.getTime()) / 86400000).intValue();
	}	
	
	/**
	 * 获取指定年份的天数
	 * @param year
	 * @return
	 */
	public static int getDaysOfYear(int year)throws Exception{
		if(isLeapYear(year)){
			return 366;
		}
		return 365;
	}
	
	/**
	 * 获取指定日期在当年的第几天
	 * @param date
	 * @return
	 */
	public static int getDayInYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_YEAR);
	}	
	
	/**
	 * 根据所给年、月、日，检验是否为合法日期。
	 *
	 * @param yyyy
	 *            4位年
	 * @param MM
	 *            月
	 * @param dd
	 *            日
	 * @return
	 */
	public static boolean verityDate(int yyyy, int MM, int dd) {
		boolean flag = false;

		if (MM >= 1 && MM <= 12 && dd >= 1 && dd <= 31) {
			if (MM == 4 || MM == 6 || MM == 9 || MM == 11) {
				if (dd <= 30)
					flag = true;
			} else if (MM == 2) {
				if (yyyy % 100 != 0 && yyyy % 4 == 0 || yyyy % 400 == 0) {
					if (dd <= 29)
						flag = true;
				} else if (dd <= 28)
					flag = true;
			} else
				flag = true;
		}
		return flag;
	}	
	
	/**
	 * 根据所给年、月、日，得到日期(java.sql.Date类型)，注意：没有时间，只有日期。
	 * 年、月、日不合法，会抛IllegalArgumentException(不需要catch)
	 *
	 * @param yyyy
	 *            4位年
	 * @param MM
	 *            月
	 * @param dd
	 *            日
	 * @return 日期
	 */
	public static java.sql.Date getDate(int yyyy, int MM, int dd) {
		if (!verityDate(yyyy, MM, dd))
			throw new IllegalArgumentException("This is illegimate date!");

		Calendar oneCalendar = Calendar.getInstance();
		oneCalendar.clear();
		oneCalendar.set(yyyy, MM - 1, dd);
		return new java.sql.Date(oneCalendar.getTime().getTime());
	}	
	
	/**
	 * 根据传入的日期和传入天数, 返回新的日期 ; 
	 * 	正数返回往后的日期 负数返回往前的日期
	 * @param date
	 * @param days
	 * @return
	 */
	public static java.sql.Date getNewDay(java.sql.Date date, int days) {
		java.sql.Date tmp = java.sql.Date.valueOf("1970-01-01");
		tmp.setTime(date.getTime() + (long)86400000*days);
		return tmp;
	}	
	
	/**
	 * 根据传入的起始日期和传入月数，返回信息的日期 只传入返回往后的日期
	 * @param date
	 * @param months
	 * @return
	 */
	public static java.sql.Date getNewDate(java.sql.Date date, int months) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);

		ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) + months);
		int yyyy = ca.get(Calendar.YEAR);
		int DD = ca.get(Calendar.DATE);
		int MM = ca.get(Calendar.MONTH);
		ca.clear();
		ca.set(yyyy, MM, DD);
		
		return new java.sql.Date(ca.getTime().getTime());
	}
	
	/**
	 * 得到当前日期(java.sql.Date类型)
	 * 	注意：返回的时间只到秒
	 * @return
	 */
	public static java.sql.Date getCurrentDate() {
		Calendar oneCalendar = Calendar.getInstance();
		int year = oneCalendar.get(Calendar.YEAR);
		int month = oneCalendar.get(Calendar.MONTH);
		int day = oneCalendar.get(Calendar.DATE);
		int hours = oneCalendar.get(Calendar.HOUR_OF_DAY);
		int minute = oneCalendar.get(Calendar.MINUTE);
		int second = oneCalendar.get(Calendar.SECOND);
//		int weekday = oneCalendar.get(Calendar.DAY_OF_WEEK);
		oneCalendar.clear();
		oneCalendar.set(year, month, day, hours, minute, second);
//		oneCalendar.set(Calendar.DAY_OF_WEEK, weekday);
		return new java.sql.Date(oneCalendar.getTime().getTime());
	}
	
	/**
	 * 根据所给的起始,终止时间来计算间隔天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return 间隔天数
	 */
	public static int getIntervalDays(java.sql.Date startDate, java.sql.Date endDate) {
		long startdate = startDate.getTime();
		long enddate = endDate.getTime();
		long interval = enddate - startdate;
		int intervalday = (int) ((long) interval / (1000 * 60 * 60 * 24));
		return intervalday;
	}
	
	/**
	 * 根据所给的起始, 终止时间来计算间隔月数
	 * 
	 * @param startDate
	 *            YYYYMM
	 * @param endDate
	 *            YYYYMM
	 * @return 间隔月数
	 */
	public static int getIntervalMonths(String startDate, String endDate) {
		int startYear = Integer.parseInt(startDate.substring(0, 4));
		int startMonth = 0;
		if(startDate.substring(4, 5).equals("0")){
			startMonth = Integer.parseInt(startDate.substring(5));
		}else{
			startMonth = Integer.parseInt(startDate.substring(4, 6));
		}
		int endYear = Integer.parseInt(endDate.substring(0, 4));
		int endMonth = 0;
		if(endDate.substring(4, 5).equals("0")){
			endMonth = Integer.parseInt(endDate.substring(5));
		}
		endMonth = Integer.parseInt(endDate.substring(4, 6));
		int intervalMonth = (endYear * 12 + endMonth) - (startYear * 12 + startMonth);
		return intervalMonth;
	}
	
	/**
	 * UtilDate->SqlDate
	 * @param date
	 * @return
	 */
	public static java.sql.Date utilDate2SqlDate(java.util.Date date) {
		java.sql.Date result = null;
		if (null != date) {
			long time = date.getTime();
			result = new java.sql.Date(time);
		}
		return result;
	}	
	
	/**
	 * UtilDate->SqlTimestamp
	 * @param date
	 * @return
	 */
	public static java.sql.Timestamp utilDate2SqlTimestamp(java.util.Date date) {
		java.sql.Timestamp result = null;
		if (null != date) {
			long time = date.getTime();
			result = new java.sql.Timestamp(time);
		}
		return result;
	}		
	
	/**
	 * 判断两个日期是否为同一天(忽略时间)
	 * @param args1
	 * @param args2
	 * @return
	 */
	public static boolean isEqualDate(Date args1, Date args2) {
		if (args1 == null || args2 == null)	return false;
		java.sql.Date date1 = new java.sql.Date(args1.getTime());
		java.sql.Date date2 = new java.sql.Date(args2.getTime());
		String date1str = date1.toString();
		String date2str = date2.toString();
		if (date1str.equals(date2str))
			return true;
		else
			return false;
	}

	/**
	 * 取得给定日期的下一天
	 * @param date
	 * @return
	 */
	public static Date getNextDate(Date date) {
		if (date == null) return null;
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DAY_OF_MONTH, 1);
		Date nextdate = ca.getTime();
		return nextdate;
	}

	/**
	 * 取得给定日期的上一天
	 * @param date
	 * @return
	 */
	public static Date getPreDate(Date date) {
		if (date == null) return null;
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DAY_OF_MONTH, -1);
		Date nextdate = ca.getTime();
		return nextdate;
	}
	
	/**
	 * 获取输入"YYYYMM"前n个月的YYYYMM
	 * @param dateStr
	 * @param n
	 * @return
	 */
	public static String getPreMonth(String dateStr, int n) {
		String endStr = "";
		int endY = 0;
		int endM = 0;
		int startYear = Integer.parseInt(dateStr.substring(0, 4));
		int startMonth = 0;
		if (dateStr.substring(4, 5).equals("0")) {
			startMonth = Integer.parseInt(dateStr.substring(5));
		} else {
			startMonth = Integer.parseInt(dateStr.substring(4, 6));
		}
		endY = startYear - n / 12;
		if (n < startMonth) {
			endM = startMonth - n;
		} else if (n % 12 < startMonth) {
			endM = startMonth - n % 12;
		} else {
			endY = endY - 1;
			endM = startMonth - n % 12 + 12;
		}

		if (endM < 10) {
			endStr = "" + endY + "0" + endM;
		} else {
			endStr = "" + endY + "" + endM;
		}
		
		return endStr;
	}
	
	/**
	 * 获取指定日期上个月的最后一天
	 * @param date
	 * @return
	 */
	public static Date getPreMonthLastDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 获取本月第一天
		cal.set(Calendar.DAY_OF_MONTH, 1); 
		// 获取上个月最后一天
		return getPreDate(new Date(cal.getTime().getTime()));
	}
	
	/**
	 * 获取指定日期所属的月份的第一天
	 * @param date
	 * @return
	 */
	public static Date getFirstDayInMonth(Date date) {
		Calendar cal = Calendar.getInstance() ;
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH ,1);
		return new Date(cal.getTime().getTime()) ;
	}	
	
	/**
	 * 获取指定日期所属的月份的最后一天
	 * @param date
	 * @return
	 */
	public static Date getLastDayInMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		return getPreDate(new Date(cal.getTime().getTime()));
	}	
	
	/**
	 * 获取指定日期下个月第一天
	 * @param date
	 * @return
	 */
	public static Date getNextMonthFirstDay(Date date) {
		Calendar cal = Calendar.getInstance() ;
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH ,20);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 3);
		return new Date(cal.getTime().getTime()) ;
	}	
	
	/**
	 * 获取指定周期的指定天
	 * @param date
	 * @return
	 */
	public static Date getMonthDay(Date date,String month) {
		Calendar cal = Calendar.getInstance() ;
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH ,Integer.parseInt(date.toString().substring(8, 10)));
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + Integer.parseInt(month));
		return new Date(cal.getTime().getTime()) ;
	}	
	
	/**
	 * 把日期格式化为 yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String formatDate2String(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		return format.format(date) ;
	}	
	
	/**
	 * 根据指定的格式将日期转换为字符串
	 * @param date
	 * @param dateRegx
	 * @return
	 */
	public static String formatDate2String(Date date,String dateRegx) {
		SimpleDateFormat format = new SimpleDateFormat(dateRegx) ;
		return format.format(date) ;
	}
	
	/**
	 * 判断给定日期为星期几
	 * 
	 * @param sDate
	 * @return
	 */
	public static String getWeekFromDate(String sDate) {
		String showDate = "";
	
		if ("".equals(sDate)) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
		Date date = null;
		try {
			date = df.parse(sDate);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		int mydate = cd.get(Calendar.DAY_OF_WEEK);
	
		switch (mydate) { // mydate分别是 1---7：星期日，星期一，星期二。。。
		case 1:
			showDate = "7";
			break;
		case 2:
			showDate = "1";
			break;
		case 3:
			showDate = "2";
			break;
		case 4:
			showDate = "3";
			break;
		case 5:
			showDate = "4";
			break;
		case 6:
			showDate = "5";
			break;
		default:
			showDate = "6";
			break;
		}
	
		return showDate;
	}

	/**
	 * 获取时间
	 * @return
	 */
	public static String getTime() {
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(getCurrentDate());
		
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		month++;
		int day = cal.get(Calendar.DATE);
		int hours = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		
		return year + "年" + month + "月" + day + "日" + hours + "时" + minute + "分" + second + "秒";
	}
	
	public static String formatDate(Date date, String format) {
		if (date == null)
			return "";
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static String getDateString(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}

	public static String fixTimestamp(String str) {
		if (str.indexOf(':') == -1)
			return qualify(str) + " 00:00:00";
		else {
			int i = str.indexOf(' ');
			return qualify(str.substring(0, i)) + str.substring(i);
		}
	}

	private static String qualify(String dateStr) {
		if (dateStr.length() == 10)
			return dateStr;
		String[] sec = StringUtil.split(dateStr, "-");
		if (sec.length == 3) {
			StringBuilder buf = new StringBuilder(10);
			buf.append(sec[0]);
			buf.append("-");
			if (sec[1].length() == 1)
				buf.append("0");
			buf.append(sec[1]);
			buf.append("-");
			if (sec[2].length() == 1)
				buf.append("0");
			buf.append(sec[2]);
			return buf.toString();
		} else
			return dateStr;
	}

	public static String fixTime(String str) {
		if (str.indexOf(':') == -1)
			return "00:00:00";
		int b = str.indexOf(' '), e = str.indexOf('.');
		if (b == -1)
			b = 0;
		if (e == -1)
			e = str.length();
		return str.substring(b, e);
	}

	public static String getHours(long milliSecs) {
		long h = milliSecs / 3600000, hm = milliSecs % 3600000;
		long m = hm / 60000, mm = hm % 60000;
		long s = mm / 1000, sm = mm % 1000;
		return StringUtil.concat(Long.toString(h), ":", Long.toString(m), ":",
				Long.toString(s), ".", Long.toString(sm));
	}

	public static int daysInMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static int dayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static int yearOf(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	public static int dayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	public static int dayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static String toString(Date date) {
		if (date == null)
			return "";
		Timestamp t = new Timestamp(date.getTime());
		return t.toString();
	}

	public static Date incYear(Date date, int years) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, years);
		return cal.getTime();
	}

	public static Date incMonth(Date date, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	public static int hourOfDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static Date incDay(Date date, long days) {
		return new Date(date.getTime() + 86400000 * days);
	}

	public static Date incSecond(Date date, long seconds) {
		return new Date(date.getTime() + 1000 * seconds);
	}

}
