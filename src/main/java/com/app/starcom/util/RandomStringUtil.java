package com.app.starcom.util;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class RandomStringUtil {
	
	public final static String RANDOM_ALPHANUMERICSTRING_DATA = "ABCDEFGHIJKLNMOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	public final static String RANDOM_ALPHABETIC_DATA = "ABCDEFGHIJKLNMOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	public final static String RANDOM_NUMERIC_DATA = "0123456789";
	public static String RANDOM_DATA = null; 
	
	public static String randomValues(int l, String randomType) {
		StringBuilder s = new StringBuilder(l);
		int i;
		for(i = 0; i < l; i++) {
			int ch = (int)(randomType.length() * Math.random());
			s.append(randomType.charAt(ch));
		}
		return s.toString();
	}
	
	public static String randomAlphanumeric(int l, String type) {
		String randomAlpha = randomValues(l, type);
		return randomAlpha;
	}
	
	public static String randomAlphabetic(int l, String type) {
		String randomAlphabetic = randomValues(l, type);
		return randomAlphabetic; //randomAlpahbetic().toUpper().toLower() //takes a parameter
	}
	
	public static String randomNumeric(int l, String type) {
		String randomNumeric = randomValues(l, type);
		return randomNumeric;
	}
	
	public static String random(int l, String type) {
		RandomStringUtil.RANDOM_DATA = type;
		String randomData = randomValues(l, RANDOM_DATA);
		return randomData;
	}
	
	public static String usingRandomUUID() {
		UUID randomUUID = UUID.randomUUID();
		return randomUUID.toString().replaceAll("-", "");
	}
	
	public static void main(String[] args) {
		System.out.println(RandomStringUtil.randomAlphanumeric(20, RandomStringUtil.RANDOM_ALPHANUMERICSTRING_DATA));
		System.out.println(RandomStringUtil.usingRandomUUID());
		System.out.println(RandomStringUtil.randomAlphabetic(10, RandomStringUtil.RANDOM_ALPHABETIC_DATA));
		System.out.println(RandomStringUtil.random(10, "102*hadkKADHAS^%$#@!*^"));
		System.out.println(RandomStringUtil.randomNumeric(10, RandomStringUtil.RANDOM_NUMERIC_DATA));
	}
}
