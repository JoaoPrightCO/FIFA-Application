package com.aroska.fifa.requests.dtos.administration.core;

import lombok.Data;

@Data
public class JogoDto {

	private Integer id;
	private Integer idEquipa1;
	private Integer idEquipa2;
	private Integer idEvento;
	private String localizacao;
	private Integer durProlongamento;
	private String equipaVencedora;
	private Integer golosEquipa1;
	private Integer golosEquipa2;
	private Integer nrSubstituicoes;
	private Integer nrFaltas;
	private Integer posseEquipa1;
	private Integer posseEquipa2;
	private String estMetereologico;
	
	public String[] getValues() {
    	String[] values = new String[3];
    	
    	values[0] = getLocalizacao();
    	values[1] = getEquipaVencedora();
    	values[2] = getEstMetereologico();
    	
    	return values;
    }
}
