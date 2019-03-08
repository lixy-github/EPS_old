package com.broadcontact.common.tools;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil implements Cloneable, Serializable {
	public static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 构造函数,默认当天日期
	 */
	public DateUtil() {
		GregorianCalendar todaysDate = new GregorianCalendar();
		year = todaysDate.get(Calendar.YEAR);
		month = todaysDate.get(Calendar.MONTH) + 1;
		day = todaysDate.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 指定构造日期
	 * 
	 * @param yyyy
	 * @param m
	 * @param d
	 */
	public DateUtil(int yyyy, int mm, int dd) {
		year = yyyy;
		month = mm;
		day = dd;
		if (!isValid())
			throw new IllegalArgumentException();
	}

	/**
	 * 转化日期，转化错误返回null
	 * 
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static Date parse(String pattern, String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(date);
		} catch (Exception e) {
			throw new Exception("日期格式错误:" + date);
		}
	}

	/**
	 * 转化成sql包的date对象，转化错误返回null
	 * 
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static java.sql.Date parseSqlDate(String pattern, String date)
			throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return new java.sql.Date(sdf.parse(date).getTime());
		} catch (Exception e) {
			throw new Exception("日期格式错误:" + date);
		}
	}

	/**
	 * 在页面上生成天选择列表
	 * 
	 * @param selectday
	 *          要选择的天
	 * @return 返回列表字符串
	 */
	public static String getDayList(String selectday) {
		String daylist = "";
		String value = "";
		String tstr = selectday == null ? "" : selectday;
		int i = 0;
		for (i = 1; i < 32; i++) {
			value = i < 10 ? "0" + i : "" + i;
			if (tstr.equals(value))
				daylist += "<option value=\"" + value + "\" selected>" + value
						+ "</option>\n";
			else
				daylist += "<option value=\"" + value + "\">" + value + "</option>\n";
		}
		return daylist;
	}

	/**
	 * 在页面上生成月份选择列表
	 * 
	 * @param selectmonth
	 *          要选择的月份
	 * @return 返回列表字符串
	 */
	public static String getMonthList(String selectmonth) {
		String monthlist = "";
		String value = "";
		String tstr = selectmonth == null ? "" : selectmonth;
		int i = 0;
		for (i = 1; i < 13; i++) {
			value = i < 10 ? "0" + i : "" + i;
			if (tstr.equals(value))
				monthlist += "<option value=\"" + value + "\" selected>" + value
						+ "</option>\n";
			else
				monthlist += "<option value=\"" + value + "\">" + value + "</option>\n";
		}
		return monthlist;
	}

	/**
	 * 在页面上生成年份选择列表
	 * 
	 * @param selectyear
	 *          要选择的年份
	 * @param start
	 *          开始年份
	 * @param end
	 *          结束年份
	 * @return 返回列表字符串
	 */
	public static String getYearList(String selectyear, int start, int end) {
		String yearlist = "";
		String tstr = selectyear == null ? "" : selectyear;
		String value = "";
		int i = 0;
		for (i = start; i <= end; i++) {
			value = i + "";
			if (tstr.equals(value))
				yearlist += "<option value=\"" + value + "\" selected>" + value
						+ "</option>\n";
			else
				yearlist += "<option value=\"" + value + "\">" + value + "</option>\n";
		}
		return yearlist;
	}

	/**
	 * 得到当前的Timestamp字符串
	 * 
	 * @return
	 */
	public static String getNowTime() {
		return new Timestamp(new Date().getTime()).toString();
	}

	/**
	 * 用整数付日期值
	 * 
	 * @param yyyy
	 * @param mm
	 * @param dd
	 */
	public void setDate(int yyyy, int mm, int dd) {
		year = yyyy;
		month = mm;
		day = dd;
	}

	/**
	 * 用字符串付日期
	 * 
	 * @param yyyy
	 * @param mm
	 * @param dd
	 */
	public void setDate(String yyyy, String mm, String dd) {
		try {
			setDate(Integer.parseInt(yyyy), Integer.parseInt(mm), Integer
					.parseInt(dd));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param n
	 */
	public void advance(int n) {
		fromJulian(toJulian() + n);
	}

	/**
	 * 取日
	 * 
	 * @return
	 */
	public int getDay() {
		return day;
	}

	/**
	 * 取月
	 * 
	 * @return
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * 取年
	 * 
	 * @return
	 */
	public int getYear() {
		return year;
	}

	/**
	 * 取星期
	 * 
	 * @return
	 */
	public int weekday() {
		return (toJulian() + 1) % 7 + 1;
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param strFormat
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		return (new SimpleDateFormat(format)).format(date);
	}

	/**
	 * 以默认的格式格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return dateFormat.format(date);
	}

	/**
	 * 取两个日期之间的天数
	 * 
	 * @param b
	 * @return
	 */
	public int daysBetween(DateUtil du) {
		int tNum = 0;
		tNum = toJulian() - du.toJulian();
		if (tNum < 0) {
			tNum = (-1) * tNum;
		}
		return tNum;
	}

	/**
	 * A string representation of the day
	 * 
	 * @return a string representation of the day
	 */

	public String toString() {
		return "Day[" + year + "," + month + "," + day + "]";
	}

	/**
	 * Makes a bitwise copy of a Day object
	 * 
	 * @return a bitwise copy of a Day object
	 */

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			return null;
		}
	}

	/**
	 * Compares this Day against another object
	 * 
	 * @param obj
	 *          another object
	 * @return true if the other object is identical to this Day object
	 */

	public boolean equals(Object obj) {
		if (!getClass().equals(obj.getClass()))
			return false;
		DateUtil b = (DateUtil) obj;
		return day == b.day && month == b.month && year == b.year;
	}

	/**
	 * Computes the number of days between two dates
	 * 
	 * @return true iff this is a valid date
	 */

	private boolean isValid() {
		DateUtil t = new DateUtil();
		t.fromJulian(this.toJulian());
		return t.day == day && t.month == month && t.year == year;
	}

	/**
	 * @return The Julian day number that begins at noon of this day Positive year
	 *         signifies A.D., negative year B.C. Remember that the year after 1
	 *         B.C. was 1 A.D.
	 * 
	 * A convenient reference point is that May 23, 1968 noon is Julian day
	 * 2440000.
	 * 
	 * Julian day 0 is a Monday.
	 * 
	 * This algorithm is from Press et al., Numerical Recipes in C, 2nd ed.,
	 * Cambridge University Press 1992
	 */

	private int toJulian() {
		int jy = year;
		if (year < 0)
			jy++;
		int jm = month;
		if (month > 2)
			jm++;
		else {
			jy--;
			jm += 13;
		}
		int jul = (int) (java.lang.Math.floor(365.25 * jy)
				+ java.lang.Math.floor(30.6001 * jm) + day + 1720995.0);
		int IGREG = 15 + 31 * (10 + 12 * 1582);
		// Gregorian Calendar adopted Oct. 15, 1582
		if (day + 31 * (month + 12 * year) >= IGREG)
		// change over to Gregorian calendar
		{
			int ja = (int) (0.01 * jy);
			jul += 2 - ja + (int) (0.25 * ja);
		}
		return jul;
	}

	/**
	 * Converts a Julian day to a calendar date
	 * 
	 * This algorithm is from Press et al., Numerical Recipes in C, 2nd ed.,
	 * Cambridge University Press 1992
	 * 
	 * @param j
	 *          the Julian date
	 */

	private void fromJulian(int j) {
		int ja = j;
		int JGREG = 2299161;
		/*
		 * the Julian date of the adoption of the Gregorian calendar
		 */
		if (j >= JGREG)
		/*
		 * cross-over to Gregorian Calendar produces this correction
		 */{
			int jalpha = (int) (((float) (j - 1867216) - 0.25) / 36524.25);
			ja += 1 + jalpha - (int) (0.25 * jalpha);
		}
		int jb = ja + 1524;
		int jc = (int) (6680.0 + ((float) (jb - 2439870) - 122.1) / 365.25);
		int jd = (int) (365 * jc + (0.25 * jc));
		int je = (int) ((jb - jd) / 30.6001);
		day = jb - jd - (int) (30.6001 * je);
		month = je - 1;
		if (month > 12)
			month -= 12;
		year = jc - 4715;
		if (month > 2)
			--year;
		if (year <= 0)
			--year;
	}

	public static int SUNDAY = 1;

	public static int MONDAY = 2;

	public static int TUESDAY = 3;

	public static int WEDNESDAY = 4;

	public static int THURSDAY = 5;


	public static int FRIDAY = 6;

	public static int SATURDAY = 7;

	/** @serial */
	private int day;

	/** @serial */
	private int month;

	/** @serial */
	private int year;
}