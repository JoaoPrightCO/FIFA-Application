package com.aroska.fifa.requests.dtos.administration.core;

import java.sql.Date;

import lombok.Data;

@Data
public class RegraDesportivaDto {

	private Integer id;
	private String zona;
	private String tipo;
	private String descricao;
	private Date dataCriacao;
	private Date dataModificacao;

	public String[] getValues() {
    	String[] values = new String[3];
    	
    	values[0] = getZona();
    	values[1] = getTipo();
    	values[2] = getDescricao();
    	
    	return values;
    }
}
