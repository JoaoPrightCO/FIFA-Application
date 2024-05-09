package com.aroska.fifa.util;

public class WebhookUtil {

	public static String checkNullString(Object obj) {
		if(obj != null) { return obj.toString(); }
		else 			{ return "null"; }
	}
	
	public static boolean checkNull(Object obj) {
		if(obj != null) { return false; }
		else			{ return true; }
	}
}
