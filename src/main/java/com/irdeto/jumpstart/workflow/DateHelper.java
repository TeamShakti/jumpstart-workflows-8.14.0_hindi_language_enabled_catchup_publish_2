package com.irdeto.jumpstart.workflow;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class DateHelper {
	
	private static DateTimeFormatter XML_DATE_TIME_FORMAT = ISODateTimeFormat.dateTimeNoMillis();
	public static final String MM_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String XMLTV_DATE_TIME_FORMAT = "yyyyMMddHHmmss Z";
	public static final String XMLTV_DATE_FORMAT = "yyyyMMdd";
	public static final String XMLTV_YEAR_FORMAT = "yyyy";
	public static final String YEAR_OF_RELEASE_FORMAT= "yyyy";

	public static final DateTime convertXMLDateToDateTime(String formattedDate){
		try {
			return XML_DATE_TIME_FORMAT.parseDateTime(formattedDate);
		} catch (Exception e) {
			return null;
		}
	}

	public static final String convertDateTimeToXMLDate(DateTime dateTime){
		if (dateTime != null) {
			return XML_DATE_TIME_FORMAT.print(dateTime);
		} else {
			return null;
		}
	}
	
	public static final String convertCablelabsDurationToSecondsString(String duration){
		int durationSecond = convertCablelabsDurationToSeconds(duration);
		return String.valueOf(durationSecond);
	}
	
	public static final int convertCablelabsDurationToSeconds(String duration){
		if (duration == null) {
			return 0;
		}
		int indexOfYearsStart = 0, indexOfYearsEnd = 0;
		int indexOfMonthsStart = 0, indexOfMonthsEnd = 0;
		int indexOfDaysStart = 0, indexOfDaysEnd = 0;
		int indexOfHoursStart = 0, indexOfHoursEnd = 0;
		int indexOfMinutesStart = 0, indexOfMinutesEnd = 0;
		int indexOfSecondsStart = 0, indexOfSecondsEnd = 0;
		
		int indexOfP = duration.indexOf('P');
		if (indexOfP == -1) {
			return 0;
		} else {
			indexOfYearsStart = indexOfP + 1;
		}
		int indexOfT = duration.indexOf('T', indexOfP + 1); 
		if (indexOfT == -1) {
			return 0;
		} else {
			indexOfHoursStart = indexOfT + 1;
		}

		int indexOfY = duration.indexOf('Y', indexOfP + 1); 
		if (indexOfY == -1 || indexOfY > indexOfT) {
			indexOfYearsEnd = indexOfYearsStart;
			indexOfMonthsStart = indexOfYearsEnd;
		} else {
			indexOfYearsEnd = indexOfY;
			indexOfMonthsStart = indexOfY + 1;
		}
		int indexOfM = duration.indexOf('M', indexOfP + 1); 
		if (indexOfM == -1 || indexOfM > indexOfT) {
			indexOfMonthsEnd = indexOfMonthsStart;
			indexOfDaysStart = indexOfMonthsEnd;
		} else {
			indexOfMonthsEnd = indexOfM;
			indexOfDaysStart = indexOfM + 1;
		}
		int indexOfD = duration.indexOf('D', indexOfP + 1); 
		if (indexOfD == -1 || indexOfD > indexOfT) {
			indexOfDaysEnd = indexOfDaysStart;
		} else {
			indexOfDaysEnd = indexOfD;
		}
		int indexOfH = duration.indexOf('H', indexOfT + 1);
		if (indexOfH == -1) {
			indexOfHoursEnd = indexOfHoursStart;
			indexOfMinutesStart = indexOfHoursEnd;
		} else {
			indexOfHoursEnd = indexOfH;
			indexOfMinutesStart = indexOfH + 1;
		}
		int indexOfSecondM = duration.indexOf('M', indexOfT + 1);
		if (indexOfSecondM == -1) {
			indexOfMinutesEnd = indexOfMinutesStart;
			indexOfSecondsStart = indexOfMinutesEnd;
		} else {
			indexOfMinutesEnd = indexOfSecondM;
			indexOfSecondsStart = indexOfSecondM + 1;
		}
		int indexOfS = duration.indexOf('S', indexOfT + 1);
		if (indexOfS == -1) {
			indexOfSecondsEnd = indexOfSecondsStart;
		} else {
			indexOfSecondsEnd = indexOfS;
		}
		String years = duration.substring(indexOfYearsStart, indexOfYearsEnd);
		String months = duration.substring(indexOfMonthsStart, indexOfMonthsEnd);
		String days = duration.substring(indexOfDaysStart, indexOfDaysEnd);
		String hours = duration.substring(indexOfHoursStart, indexOfHoursEnd);
		String minutes = duration.substring(indexOfMinutesStart, indexOfMinutesEnd);
		String seconds = duration.substring(indexOfSecondsStart, indexOfSecondsEnd);
		years = StringUtils.leftPad(years, 1, '0');
		months = StringUtils.leftPad(months, 1, '0');
		days = StringUtils.leftPad(days, 1, '0');
		hours = StringUtils.leftPad(hours, 1, '0');
		minutes = StringUtils.leftPad(minutes, 1, '0');
		seconds = StringUtils.leftPad(seconds, 1, '0');
		if (!StringUtils.isNumeric(years) || !StringUtils.isNumeric(months) || !StringUtils.isNumeric(days) || !StringUtils.isNumeric(hours) || !StringUtils.isNumeric(minutes) || !StringUtils.isNumeric(seconds)) {
			return 0;
		}
		// Note that years and months have no real meaning in terms of duration and so are ignored.  We assume that content is not longer than so many days anyway, before it gets boring.
		return Integer.parseInt(days) * 24 * 60 * 60
				+ Integer.parseInt(hours) * 60 * 60
				+ Integer.parseInt(minutes) * 60
				+ Integer.parseInt(seconds);
	}
	
	public static final int convertDurationToSeconds(String duration){
		DateTime dt = null;
		try {
		DateTimeFormatter durationFormat = DateTimeFormat.forPattern("HH:mm");
		dt = durationFormat.parseDateTime(duration);
		} catch(Exception e) {
			try {
			DateTimeFormatter durationFormat = DateTimeFormat.forPattern("HH:mm:ss");
			dt = durationFormat.parseDateTime(duration);
			} catch (Exception nfe){
				// ignore
			}
		}
		if (dt != null){
			return (dt.getHourOfDay() * 60 * 60) + (dt.getMinuteOfHour() * 60) + dt.getSecondOfMinute();	
		} else {
			return 0;
		}
	}
	
	public static XMLGregorianCalendar convertStringToXmlGregorian(String dateString, String format) throws ParseException, DatatypeConfigurationException{
		DateFormat df = new SimpleDateFormat(format);
		Date date = df.parse(dateString);
        GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
        gc.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
	}

	public static XMLGregorianCalendar convertDateTimeToXmlGregorian(DateTime dateTime) throws ParseException, DatatypeConfigurationException{
        GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
        gc.setTime(dateTime.toDate());
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
	}
	
	public static DateTime convertXMLTVDateToDateTime(String xmlTvDate) {
		try {
			return DateTimeFormat.forPattern(XMLTV_DATE_TIME_FORMAT).parseDateTime(xmlTvDate);
		} catch (Exception e) {
		}
		return null;
	}

	public static DateTime convertXMLTVDate(String xmlTvDate) {
		if (xmlTvDate.length() == 4) {
			try {
				return DateTimeFormat.forPattern(XMLTV_YEAR_FORMAT).parseDateTime(xmlTvDate);
			} catch (Exception e) {
			}
		} else if (xmlTvDate.length() == 8) {
			try {
				return DateTimeFormat.forPattern(XMLTV_DATE_FORMAT).parseDateTime(xmlTvDate);
			} catch (Exception e) {
			}
		}
		return null;
	}
}
