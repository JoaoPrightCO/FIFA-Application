package com.aroska.fifa.requests.dtos.administration.auth;

import lombok.Data;

@Data
public class LoginDto {
	
    private String usernameOrEmail;
    private String password;
    
    public String[] getValues() {
    	String[] values = new String[2];
    	
    	values[0] = getUsernameOrEmail();
    	values[1] = getPassword();
    	
    	return values;
    }
}