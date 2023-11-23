package com.aroska.fifa.requests.dtos.workflows;

import lombok.Data;

@Data
public class WFAccaoDto {

	private Integer id;
	private Integer id_wf;
	private String accao;
	
	public String[] getValues() {
    	String[] values = new String[3];

    	values[0] = String.valueOf(getId());
    	values[1] = String.valueOf(getId_wf());
    	values[2] = getAccao();
    	
    	return values;
    }
}
