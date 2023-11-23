package com.aroska.fifa.requests.dtos.administration.core;

import java.sql.Date;

import lombok.Data;

@Data
public class AgenteDto {

	private Integer id;
	private String nome;
	private String morada;
	private Integer tipoAgente;
	private Integer nif;
	private String nacionalidade;
	private Date dataNasc;
	private String sexo;
	private String agencia;
	private String foto;

    public String[] getValues() {
    	String[] values = new String[6];
    	
    	values[0] = getNome();
    	values[1] = getMorada();
    	values[2] = getNacionalidade();
    	values[3] = getSexo();
    	values[4] = getAgencia();
    	values[5] = getFoto();
    	
    	return values;
    }
	
}
