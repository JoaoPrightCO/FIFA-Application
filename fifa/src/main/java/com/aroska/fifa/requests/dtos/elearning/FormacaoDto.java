package com.aroska.fifa.requests.dtos.elearning;

import java.sql.Date;

import lombok.Data;

@Data
public class FormacaoDto {

	private Integer id;
	private String nome;
	private String descricao;
	private String entidade;
	private String tipo;
	private String forma;
	private String competencias;
	private String area;
	private String trab_prop;
	private String estado;
	private Date data_pub;
	private String autor;
	private String imagem;

    public String[] getValues() {
    	String[] values = new String[12];
    	
    	values[0] = getNome();
    	values[1] = getDescricao();
    	values[2] = getEntidade();
    	values[3] = getTipo();
    	values[4] = getForma();
    	values[5] = getCompetencias();
    	values[6] = getArea();
    	values[7] = getTrab_prop();
    	values[8] = getEstado();
    	values[9] = String.valueOf(getData_pub());
    	values[10] = getAutor();
    	values[11] = getImagem();
    	
    	return values;
    }
	
}
