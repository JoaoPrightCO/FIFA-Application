package com.aroska.fifa.requests.dtos.administration.core;

import java.sql.Date;

import lombok.Data;

@Data
public class TreinadorDto {

	private Integer id;
	private String nome;
	private String morada;
	private Integer nif;
	private String nacionalidade;
	private Date dataNasc;
	private String sexo;
	private Integer idEquipa;
	private String certificacao;
	private String foto;
	
	public String[] getValues() {
    	String[] values = new String[6];
    	
    	values[0] = getNome();
    	values[1] = getMorada();
    	values[2] = getNacionalidade();
    	values[3] = getSexo();
    	values[4] = getCertificacao();
    	values[5] = getFoto();
    	
    	return values;
    }
}
