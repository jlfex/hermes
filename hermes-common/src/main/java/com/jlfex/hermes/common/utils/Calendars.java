package com.jlfex.hermes.common.utils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.common.exception.ServiceException;

/**
 * 日历工具
 */
public abstract class Calendars {

	private static final String PREFIX				= "DATE_FORMAT_";
	private static final String PATTERN_DATE		= App.config("app.pattern.date", "yyyy-MM-dd");
	private static final String PATTERN_TIME		= App.config("app.pattern.time", "HH:mm:ss");
	private static final String PATTERN_DATE_TIME	= App.config("app.pattern.datetime", "yyyy-MM-dd HH:mm:ss");
	private static final Pattern PARSE_DAYS			= Pattern.compile("^([0-9]+)d$");
	private static final Pattern PARSE_HOURS		= Pattern.compile("^([0-9]+)h$");
	private static final Pattern PARSE_MINUTES		= Pattern.compile("^([0-9]+)mi?n$");
	private static final Pattern PARSE_SECONDS		= Pattern.compile("^([0-9]+)s$");
	
	/**
	 * 读取日期格式
	 * 
	 * @param pattern
	 * @return
	 */
	public static DateFormat getDateFormat(String pattern) {
		DateFormat format = Caches.get(PREFIX + pattern, DateFormat.class);
		if (format == null) {
			format = new SimpleDateFormat(pattern);
			Caches.add(PREFIX + pattern, format);
		}
		return format;
	}
	
	/**
	 * 格式化日期
	 * 
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String format(String pattern, Date date) {
		return getDateFormat(pattern).format(date);
	}
	
	/**
	 * 格式化日期
	 * 
	 * @param pattern
	 * @return
	 */
	public static String format(String pattern) {
		return format(pattern, new Date());
	}
	
	/**
	 * 解析日期字符串
	 * 
	 * @param pattern
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String pattern, String source) throws ParseException {
		return getDateFormat(pattern).parse(source);
	}
	
	/**
	 * 解析并格式化时间日期字符串
	 * 
	 * @param parsePattern
	 * @param formatPattern
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static String parseAndFormat(String parsePattern, String formatPattern, String source) throws ParseException {
		return format(formatPattern, parse(parsePattern, source));
	}
	
	/**
	 * 解析并格式化时间日期字符串<br>
	 * 如果发生异常返回默认字符串
	 * 
	 * @param parsePattern
	 * @param formatPattern
	 * @param source
	 * @param defaultValue
	 * @return
	 */
	public static String parseAndFormat(String parsePattern, String formatPattern, String source, String defaultValue) {
		try {
			return parseAndFormat(parsePattern, formatPattern, source);
		} catch (ParseException e) {
			Logger.warn(e.getMessage());
			return defaultValue;
		}
	}
	
	/**
	 * 以默认格式格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String date(Date date) {
		return format(PATTERN_DATE, date);
	}
	
	/**
	 * 以默认格式格式化当前日期
	 * 
	 * @return
	 */
	public static String date() {
		return date(new Date());
	}
	
	/**
	 * 以默认格式格式化时间
	 * 
	 * @param date
	 * @return
	 */
	public static String time(Date date) {
		return format(PATTERN_TIME, date);
	}
	
	/**
	 * 以默认格式格式化当前时间
	 * 
	 * @return
	 */
	public static String time() {
		return time(new Date());
	}
	
	/**
	 * 以默认格式格式化日期时间
	 * 
	 * @param date
	 * @return
	 */
	public static String dateTime(Date date) {
		return format(PATTERN_DATE_TIME, date);
	}
	
	/**
	 * 以默认格式格式化当前日期时间
	 * 
	 * @return
	 */
	public static String dateTime() {
		return dateTime(new Date());
	}
	
	/**
	 * 解析默认日期字符串
	 * 
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String source) throws ParseException {
		return parse(PATTERN_DATE, source);
	}
	
	/**
	 * 解析默认时间字符串
	 * 
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static Date parseTime(String source) throws ParseException {
		return parse(PATTERN_TIME, source);
	}
	
	/**
	 * 解析默认日期时间字符串
	 * 
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDateTime(String source) throws ParseException {
		return parse(PATTERN_DATE_TIME, source);
	}
	
	/**
	 * 解析持续时间<br>
	 * 样例：3d, 4h, 5mn, 6s
	 * 
	 * @param duration
	 * @return
	 */
	public static int parseDuration(String duration) {
		// 初始化
		if (Strings.empty(duration)) duration = "30d";
		Matcher matcher = null;

		// 判断周期类型并转换成秒数
		if ((matcher = PARSE_DAYS.matcher(duration)).matches()) {
			return Integer.parseInt(matcher.group(1)) * 60 * 60 * 24;
		} else if ((matcher = PARSE_HOURS.matcher(duration)).matches()) {
			return Integer.parseInt(matcher.group(1)) * 60 * 60;
		} else if ((matcher = PARSE_MINUTES.matcher(duration)).matches()) {
			return Integer.parseInt(matcher.group(1)) * 60;
		} else if ((matcher = PARSE_SECONDS.matcher(duration)).matches()) {
			return Integer.parseInt(matcher.group(1));
		} else {
			throw new IllegalArgumentException("invalid duration pattern '" + duration + "'.");
		}
	}

	/**
	 * 增加时间
	 * 
	 * @param date
	 * @param field
	 * @param amount
	 * @return
	 */
	public static Date add(Date date, Integer field,  Integer amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}
	
	/**
	 * 增加时间
	 * 
	 * @param date
	 * @param duration
	 * @return
	 */
	public static Date add(Date date, String duration) {
		return add(date, Calendar.SECOND, parseDuration(duration));
	}
	
	/**
	 * 解析成开始时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseBeginDateTime(String date) {
		try {
			return parseDateTime(date + " 00:00:00");
		} catch (ParseException e) {
			throw new ServiceException("cannot parse '" + date + "' to begin date", e);
		}
	}
	
	/**
	 * 解析成结束时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseEndDateTime(String date) {
		try {
			return parseDateTime(date + " 23:59:59");
		} catch (ParseException e) {
			throw new ServiceException("cannot parse '" + date + "' to end date", e);
		}
	}
	
	/** 
     * 计算两个日期之间相差的天数 
     * @param smallDate 较小的时间
     * @param bigDate  较大的时间
     * @return 相差天数
	 * @throws ParseException 
     */  
    public static int daysBetween(Date smallDate,Date bigDate) throws ParseException  
    {  
    	smallDate = parse("yyyy-MM-dd", format("yyyy-MM-dd", smallDate));
    	bigDate = parse("yyyy-MM-dd", format("yyyy-MM-dd", bigDate));
        Calendar cal = Calendar.getInstance();  
        cal.setTime(smallDate);  
        long time1 = cal.getTimeInMillis();               
        cal.setTime(bigDate);  
        long time2 = cal.getTimeInMillis();       
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));         
    }  
    
	/**
	*字符串的日期格式的计算
	*/
    public static int daysBetween(String smllDate,String bigDate) throws ParseException{
        Calendar cal = Calendar.getInstance();  
        cal.setTime(parse("yyyy-MM-dd", smllDate));  
        long time1 = cal.getTimeInMillis();               
        cal.setTime(parse("yyyy-MM-dd", bigDate));  
        long time2 = cal.getTimeInMillis();       
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));   
    }
	
	
}
