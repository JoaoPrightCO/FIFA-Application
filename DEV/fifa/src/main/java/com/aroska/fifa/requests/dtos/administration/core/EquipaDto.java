package com.aroska.fifa.requests.dtos.administration.core;

import java.sql.Date;

import lombok.Data;

@Data
public class EquipaDto {

	private Integer id;
	private String nome;
	private String morada;
	private Integer zona;
	private Date dataCriacao;
	private Integer nrMembros;
	private String logotipo;
	
	public String[] getValues() {
    	String[] values = new String[3];
    	
    	values[0] = getNome();
    	values[1] = getMorada();
    	values[2] = getLogotipo();
    	
    	return values;
    }
}
