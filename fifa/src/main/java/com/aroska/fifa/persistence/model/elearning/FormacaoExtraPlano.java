package com.aroska.fifa.persistence.model.elearning;

import java.sql.Date;
import java.sql.Time;

import com.aroska.fifa.constants.TableNames;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = TableNames.FORMACOES_EXTRAPLANO)
public class FormacaoExtraPlano {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(length = 500)
    private String descricao;
	
	@Column(length = 60)
    private String unidade_organica;
	
	@Column(length = 60)
    private String requisitante;

	@Column(length = 60)
    private String area;

	@Column(length = 60)
    private String justificacao;

	@Column(length = 60)
    private String objectivos_esp;

	@Column(length = 60)
    private Integer carga_horaria;

	@Column(length = 60)
    private String trab_prop;

	@Column(length = 60)
    private String entidade;

	@Column(length = 60)
    private String tipo;

	@Column(length = 60)
    private String forma_org;
	
	@Column(length = 60)
    private Date data_prevista;

	@Column(length = 60)
    private Integer horario_realizacao;

	@Column(length = 60)
    private String local_realizacao;

	@Column(length = 60)
    private Integer custo;

	@Column(length = 60)
    private String conteudo_prog;

	@Column(length = 60)
    private String estado;

	@Column(length = 60)
    private Date data_pub;

	@Column(length = 60)
    private String autor;
}
