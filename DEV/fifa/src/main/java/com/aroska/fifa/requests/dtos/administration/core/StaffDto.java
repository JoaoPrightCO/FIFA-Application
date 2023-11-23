package com.aroska.fifa.requests.dtos.administration.core;

import java.sql.Date;

import lombok.Data;

@Data
public class StaffDto {

	private Integer id;
	private String nome;
	private String morada;
	private String tipoStaff;
	private Integer nif;
	private String nacionalidade;
	private Date dataNasc;
	private String sexo;
	private String cargo;
	private String foto;
	
	public String[] getValues() {
    	String[] values = new String[7];
    	
    	values[0] = getNome();
    	values[1] = getMorada();
    	values[2] = getTipoStaff();
    	values[3] = getNacionalidade();
    	values[4] = getSexo();
    	values[5] = getCargo();
    	values[6] = getFoto();
    	
    	return values;
    }
}
