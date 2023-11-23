package com.aroska.fifa.persistence.model.elearning;

import java.sql.Date;

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
@Table(name = TableNames.FORMACOES)
public class Formacao {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(length = 60)
    private String nome;
	
	@Column(length = 500)
    private String descricao;
	
	@Column(length = 60)
    private String entidade;
	
	@Column(length = 60)
    private String tipo;

	@Column(length = 60)
    private String forma;

	@Column(length = 60)
    private String competencias;

	@Column(length = 60)
    private String area;

	@Column(length = 60)
    private String trab_prop;

	@Column(length = 60)
    private String estado;

	@Column(length = 60)
    private Date data_pub;

	@Column(length = 60)
    private String autor;

	@Column(length = 60)
    private String imagem;
}
