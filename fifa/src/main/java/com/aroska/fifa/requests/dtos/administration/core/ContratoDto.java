package com.aroska.fifa.requests.dtos.administration.core;

import java.sql.Date;

import lombok.Data;

@Data
public class ContratoDto {

	private Integer id;
	private Integer ent1Id;
	private Integer ent2Id;
	private String tipoContrato;
	private String descrContrato;
	private String estado;
	private Date dataCriacao;
	private Date dataModificacao;
	private String motivo;
	private String documento;
	
	public String[] getValues() {
    	String[] values = new String[5];
    	
    	values[0] = getTipoContrato();
    	values[1] = getDescrContrato();
    	values[2] = getEstado();
    	values[3] = getMotivo();
    	values[4] = getDocumento();
    	
    	return values;
    }
}
