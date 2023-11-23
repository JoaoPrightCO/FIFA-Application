package com.aroska.fifa.requests.dtos.administration.core;

import java.sql.Date;

import lombok.Data;

@Data
public class SeguroDto {

	private Integer id;
	private String entidade;
	private String tipo;
	private Date dataInicioContr;
	private Integer pagamentoMensal;
	private String descricao;
	private Date dataCriacao;
	private Date dataModificacao;
	
	public String[] getValues() {
    	String[] values = new String[3];
    	
    	values[0] = getEntidade();
    	values[1] = getTipo();
    	values[2] = getDescricao();
    	
    	return values;
    }
}
