package com.aroska.fifa.requests.dtos.administration.core;

import java.sql.Date;

import lombok.Data;

@Data
public class ConflitoDto {

	private Integer id;
	private Integer ent1Id;
	private Integer ent2Id;
	private Integer entgId;
	private String tipoConflito;
	private String descrConflito;
	private String estado;
	private Date dataCriacao;
	private Date dataModificacao;
	private Date dataVerdicto;
	private String motivoRecusa;
	private String documento;
	
	public String[] getValues() {
    	String[] values = new String[5];
    	
    	values[0] = getTipoConflito();
    	values[1] = getDescrConflito();
    	values[2] = getEstado();
    	values[3] = getMotivoRecusa();
    	values[4] = getDocumento();
    	
    	return values;
    }
}
