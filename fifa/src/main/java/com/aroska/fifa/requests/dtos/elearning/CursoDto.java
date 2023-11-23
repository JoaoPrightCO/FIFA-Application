package com.aroska.fifa.requests.dtos.elearning;

import java.sql.Date;

import lombok.Data;

@Data
public class CursoDto {

	private Integer id;
	private Integer id_formacao;
	private String nome;
	private String descricao;
	private String lib_filepath;
	private String intervencao;
	private Integer nr_ficheiros;
	private Integer carga_horaria;
	private String estado;
	private Date data_pub;
	private String autor;
	private String imagem;

    public String[] getValues() {
    	String[] values = new String[11];
    	
    	values[0] = String.valueOf(getId_formacao());
    	values[1] = getNome();
    	values[2] = getDescricao();
    	values[3] = getLib_filepath();
    	values[4] = getIntervencao();
    	values[5] = String.valueOf(getNr_ficheiros());
    	values[6] = String.valueOf(getCarga_horaria());
    	values[7] = getEstado();
    	values[8] = String.valueOf(getData_pub());
    	values[9] = getAutor();
    	values[10] = getImagem();
    	
    	return values;
    }
	
}
