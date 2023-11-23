package com.aroska.fifa.requests.dtos.administration.core;

import java.sql.Date;

import lombok.Data;

@Data
public class EntidadeDto {

	private Integer id;
	private String nome;
	private String morada;
	private String tipoEntidade;
	private Date dataFundacao;
	private String logotipo;
	
	public String[] getValues() {
    	String[] values = new String[4];
    	
    	values[0] = getNome();
    	values[1] = getMorada();
    	values[2] = getTipoEntidade();
    	values[3] = getLogotipo();
    	
    	return values;
    }
}
