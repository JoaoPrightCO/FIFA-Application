package com.aroska.fifa.util;

import com.aroska.fifa.constants.TiposUtilizador;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class AccountUtil {
	
	public static boolean validateLogin(HttpServletRequest request, int userType) {
		
		HttpSession session = request.getSession();
		
		if(session != null) {	
			
			if(session.getAttribute("loggedIn") == null) {
				System.out.println("User not logged in");
				return false;
			}
			
			if(userType == TiposUtilizador.NO_VERIFICATION.getId()) {
				System.out.println("No verification needed for request");
				return true;
			}
		
			if(session.getAttribute("userType") != null && userType != 0) {
				if(session.getAttribute("userType").equals(userType)) {
					System.out.println("User type validated");
					return true;
				} else { System.out.println("Invalid user type"); }
			} else { System.out.println("Unable to fetch user type"); }
			
		} else { System.out.println("Null session identified"); }
		
		return false;
	}
}
