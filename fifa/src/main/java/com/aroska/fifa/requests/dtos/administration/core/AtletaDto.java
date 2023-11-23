package com.aroska.fifa.requests.dtos.administration.core;

import java.sql.Date;

import lombok.Data;

@Data
public class AtletaDto {

	private Integer id;
    private String nome;
    private String morada;
    private String tipoAtleta;
    private Integer nif;
    private String nacionalidade;
    private Date data_nasc;
    private String sexo;
    private Integer idEquipa;
    private String posicao;
    private Integer peso;
    private Integer altura;
    private Integer posseBola;
    private Integer golosTotal;
    private Integer golosMedia;
    private Integer substituicoes;
    private String foto;

    public String[] getValues() {
    	String[] values = new String[7];
    	
    	values[0] = getNome();
    	values[1] = getMorada();
    	values[2] = getTipoAtleta();
    	values[3] = getNacionalidade();
    	values[4] = getSexo();
    	values[5] = getPosicao();
    	values[6] = getFoto();
    	
    	return values;
    }
}
