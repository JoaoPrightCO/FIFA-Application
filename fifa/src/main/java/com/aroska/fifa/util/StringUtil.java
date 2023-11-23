package com.aroska.fifa.util;

public class StringUtil {

	public static boolean validate(String value) {

		if(value.equals(null) || value.isEmpty() || value.isBlank()) {
			return false;
		}

		return true;
	}
	
	public static boolean validate(String[] values) {
		
		for(String s : values) {
			System.out.println("Validating \'" + s + "\'");
			if(s.equals(null) || s.isEmpty() || s.isBlank()) {
				logValidation(s);
				return false;
			}
			System.out.println("OK");
		}
		
		return true;
	}
	
	private static void logValidation(String s) {
		System.out.println("Validating \'" + s + "\'\n" +
				"null: " + s.equals(null) + "\n" +
				"empty: " + s.isEmpty() + "\n" +
				"blank: " + s.isBlank());
	}
	
}
