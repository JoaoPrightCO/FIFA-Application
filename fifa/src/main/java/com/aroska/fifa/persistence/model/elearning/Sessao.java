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
@Table(name = TableNames.SESSOES)
public class Sessao {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	//TODO - foreign key - ADD
	@Column(length = 60)
    private Integer id_curso;
	
	@Column(length = 60)
    private String nome;
	
	@Column(length = 60)
    private String descricao;
	
	@Column(length = 60)
    private String lib_filepath;

	@Column(length = 60)
    private String intervencao;

	@Column(length = 60)
    private Integer nr_ficheiros;

	@Column(length = 60)
    private Integer carga_horaria;

	@Column(length = 60)
    private String estado;

	@Column(length = 60)
    private Date data_pub;

	@Column(length = 60)
    private String autor;

	@Column(length = 60)
    private String imagem;
}
