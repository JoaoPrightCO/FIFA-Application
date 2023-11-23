package com.aroska.fifa.requests.dtos.administration.auth;

import java.sql.Date;

import lombok.Data;

@Data
public class UtilizadorDto {

	private Integer id;
	private String nome;
	private String username;
	private String email;
	private String password;
	private String morada;
	private Integer tipoUtilizador;
	private Integer nif;
	private String nacionalidade;
	private Date dataNasc;
	private String sexo;
	private String foto;
	
	public String[] getValues() {
    	String[] values = new String[12];

    	values[0] = String.valueOf(getId());
    	values[1] = getNome();
    	values[2] = getUsername();
    	values[3] = getEmail();
    	values[4] = getPassword();
    	values[5] = getMorada();
    	values[6] = String.valueOf(tipoUtilizador);
    	values[7] = String.valueOf(nif);
    	values[8] = getNacionalidade();
    	values[9] = String.valueOf(dataNasc);
    	values[10] = getSexo();
    	values[11] = getFoto();
    	
    	return values;
    }
	
}
