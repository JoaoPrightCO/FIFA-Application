package com.aroska.fifa.requests.dtos.elearning;

import java.sql.Date;

import lombok.Data;

@Data
public class SessaoDto {

	private Integer id;
	private Integer id_curso;
	private String nome;
	private String descricao;
	private String lib_filepath;
	private String intervencao;
	private Integer nr_ficheiros;
	private int carga_horaria;
	private String estado;
	private Date data_pub;
	private String autor;
	private String imagem;

    public String[] getValues() {
    	String[] values = new String[12];
    	
    	values[0] = String.valueOf(getId());
    	values[1] = String.valueOf(getId_curso());
    	values[2] = getNome();
    	values[3] = getDescricao();
    	values[4] = getLib_filepath();
    	values[5] = getIntervencao();
    	values[6] = String.valueOf(getNr_ficheiros());
    	values[7] = String.valueOf(getCarga_horaria());
    	values[8] = getEstado();
    	values[9] = String.valueOf(getData_pub());
    	values[10] = getAutor();
    	values[11] = getImagem();
    	
    	return values;
    }
	
}
