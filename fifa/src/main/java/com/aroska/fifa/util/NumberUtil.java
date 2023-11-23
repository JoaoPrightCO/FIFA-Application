package com.aroska.fifa.util;

public class NumberUtil {

	public static boolean validate(int[] values) {
		for(int i : values) {
			if(i != 0) {
				return false;
			}
		}
		return true;
	}
	
}
