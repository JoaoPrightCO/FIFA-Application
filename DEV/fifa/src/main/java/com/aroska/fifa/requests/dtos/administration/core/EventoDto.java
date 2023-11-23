package com.aroska.fifa.requests.dtos.administration.core;

import java.sql.Date;

import lombok.Data;

@Data
public class EventoDto {

	private Integer id;
	private String nome;
	private String entidade;
	private String localizacao;
	private Date data;
	private Date dataCriacao;
	private Date dataModificacao;
	
	public String[] getValues() {
    	String[] values = new String[3];
    	
    	values[0] = getNome();
    	values[1] = getEntidade();
    	values[2] = getLocalizacao();
    	
    	return values;
    }
}
