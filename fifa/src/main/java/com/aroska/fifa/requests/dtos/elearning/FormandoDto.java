package com.aroska.fifa.requests.dtos.elearning;

import java.sql.Date;

import lombok.Data;

@Data
public class FormandoDto {

	private Integer id_user;
	private Integer id_formacao;
	private Date data_inicio;
	private Date data_fim;
	private String estado;
	
    public String[] getValues() {
    	String[] values = new String[3];
    	
    	values[0] = String.valueOf(getData_inicio());
    	values[1] = String.valueOf(getData_fim());
    	values[2] = getEstado();
    	
    	return values;
    }
	
}
