package com.aroska.fifa.requests.dtos.workflows;

import lombok.Data;

@Data
public class WFWorkflowDto {

	private Integer id;
	private Integer id_user;
	private String nome;
	
	public String[] getValues() {
    	String[] values = new String[3];

    	values[0] = String.valueOf(getId());
    	values[1] = String.valueOf(getId_user());
    	values[2] = getNome();
    	
    	return values;
    }
}
