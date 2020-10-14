package com.dtx.demo.common.utils;

import java.util.Random;

/**
 * 随机字符串生成器
 * @author hewenhui
 * @date 2018年3月9日
 */
public class RandomTextGenerator {
	
	private static final char[] TABLE = 
		{'0','1','2','3','4','5','6','7','8','9',
		 'a','b','c','d','e','f','g','h','i','j',
		 'k','l','m','n','o','p','q','r','s','t',
		 'u','v','w','x','y','z','A','B','C','D',
		 'E','F','G','H','I','J','K','L','M','N',
		 'O','P','Q','R','S','T','U','V','W','X',
		 'Y','Z'};
	
	public static String getRandomText8() {
		return getRandomText(8);
	}
	
	public static String getRandomText16() {
		return getRandomText(16);
	}
	
	public static String getRandomText32() {
		return getRandomText(32);
	}
	
	public static String getRandomText(int len) {
		StringBuilder sb = new StringBuilder(len);
		for(int i=0; i<len; i++) {
			int index = (int) (new Random().nextLong() & 61);
			sb.append(TABLE[index]);
		}
		return sb.toString();
	}
	
	public static char[] getTable() {
		return TABLE;
	}
	
}
