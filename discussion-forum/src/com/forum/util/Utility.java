package com.forum.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class Utility {
	public static Date getCurrentDate() {
		return Calendar.getInstance(TimeZone.getTimeZone("IST")).getTime();
	}
	
	public static boolean isNotNullAndEmpty(String arg) {
		if (arg != null && !arg.equals("")) {
			return true;
		}
		return false;
	}
	//try-catch block included to catch the IO Exception which might be raised by readLine() method
	public static String inputFromUser() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input="";
		try {
			input = br.readLine();
		}catch(IOException e){
			System.out.println("IO Exception thrown.Please try again.");
		}
		return input;
	}
}
