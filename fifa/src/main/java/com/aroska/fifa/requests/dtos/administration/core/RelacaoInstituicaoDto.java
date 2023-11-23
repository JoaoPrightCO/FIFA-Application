package com.aroska.fifa.requests.dtos.administration.core;

import java.sql.Date;

import lombok.Data;

@Data
public class RelacaoInstituicaoDto {

	private Integer id;
	private String entidade;
	private String instituicao;
	private String relacao;
	private Date dataCriacao;
	private Date dataModificacao;
	
	public String[] getValues() {
    	String[] values = new String[3];
    	
    	values[0] = getEntidade();
    	values[1] = getInstituicao();
    	values[2] = getRelacao();
    	
    	return values;
    }
}
