package com.aroska.fifa.util;

import jakarta.servlet.http.Cookie;

public class CookieUtil {

	public static boolean validate(Cookie[] cookies, String cookieName, String expectedValue) {
		
		if(cookies.length>0) {
			for (Cookie c : cookies) {
				if(c.getName().equals(cookieName) && c.getValue().equals(expectedValue)) { return true; }
			}
		}
		
		return false;
	}
}
