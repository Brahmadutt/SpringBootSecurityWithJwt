package com.wow.security.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateUtil {

	public static final String DD$MM$YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";

	public static final String DD_MM_YYYY = "dd-MM-yyyy";
	
	public static final String DDMMYYYY = "ddMMyyyy";
	
	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	public static final String DD_MMM_YYYY = "dd MMM yyyy";
	
	public static final String DD$MM$YYYY = "dd/MM/yyyy";

	
	public static final String DD$MM$YYYY_a ="dd/MM/yyyy hh:mm a";
	
	public static final String DD_MM_YYYY_a ="dd-MM-yyyy hh:mm a";
	
	public static final String hh_mm_a = "hh:mm a";
	
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	
	public static final String DD$MM$YYYY_HH_MM = "dd/MM/yyyy HH:mm";
	
	public static final String DD_MM_YYYY_HH_MM_SS = "dd-MM-yyyy HH:mm:ss";

	public static Date stringToDate(String date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(false);
		try {
			return date != null ? sdf.parse(date) : null;
		} catch (ParseException e) {
			return null;
		}
	}

	public static String dateToString(Date date, String pattern) {
		return date!=null&&StringUtils.isNotBlank(pattern)?new SimpleDateFormat(pattern).format(date):null;
	}

	public static Date addDays(Date date, int days) {
		LocalDateTime nextDay = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).plusDays(days);
		return Date.from(ZonedDateTime.of(nextDay, ZoneId.systemDefault()).toInstant());
	}
	public static Date localDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

}
