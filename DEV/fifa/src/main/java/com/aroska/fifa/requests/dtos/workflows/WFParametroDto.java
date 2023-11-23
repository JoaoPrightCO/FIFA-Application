package com.aroska.fifa.requests.dtos.workflows;

import lombok.Data;

@Data
public class WFParametroDto {

	private Integer id;
	private Integer id_accao;
	private String tipoEntidade;
	private String campo;
	private String valor;
	
	public String[] getValues() {
    	String[] values = new String[5];
    	
    	values[0] = String.valueOf(getId());
    	values[1] = String.valueOf(getId_accao());
    	values[2] = getTipoEntidade();
    	values[3] = getCampo();
    	values[4] = getValor();
    	
    	return values;
    }
}
