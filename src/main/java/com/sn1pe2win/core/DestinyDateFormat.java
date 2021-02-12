package com.sn1pe2win.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**Converts a Destiny-date format to a java.util.date object*/
public class DestinyDateFormat {

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	
	/**Date in the format: yyyy-MM-dd'T'HH:mm:ss'Z'
	 * @throws ParseException - If the date as string does not match the above named pattern*/
	public static Date toDate(String date) throws ParseException {
		return FORMAT.parse(date);
	}
}
