package com.aroska.fifa.requests.dtos.elearning;

import java.sql.Date;

import lombok.Data;

@Data
public class FormacaoExtraPlanoDto {

	private Integer id;
	private String descricao;
	private String unidade_organica;
	private String requisitante;
	private String area;
	private String justificacao;
	private String objectivos_esp;
	private Integer carga_horaria;
	private String trab_prop;
	private String entidade;
	private String tipo;
	private String forma_org;
	private Date data_prevista;
	private Integer horario_realizacao;
	private String local_realizacao;
	private Integer custo;
	private String conteudo_prog;
	private String estado;
	private Date data_pub;
	private String autor;

    public String[] getValues() {
    	String[] values = new String[19];
    	
    	values[0] = getDescricao();
    	values[1] = getUnidade_organica();
    	values[2] = getRequisitante();
    	values[3] = getArea();
    	values[4] = getJustificacao();
    	values[5] = getObjectivos_esp();
    	values[6] = String.valueOf(getCarga_horaria());
    	values[7] = getTrab_prop();
    	values[8] = getEntidade();
    	values[9] = getTipo();
    	values[10] = getForma_org();
    	values[11] = String.valueOf(getData_prevista());
    	values[12] = String.valueOf(getHorario_realizacao());
    	values[13] = getLocal_realizacao();
    	values[14] = String.valueOf(getCusto());
    	values[15] = getConteudo_prog();
    	values[16] = getEstado();
    	values[17] = String.valueOf(getData_pub());
    	values[18] = getAutor();
    	
    	return values;
    }
	
}
