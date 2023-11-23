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
@Table(name = TableNames.FORMANDOS)
public class Formando {
	
	//TODO
	//FOREIGN KEYS - ADD
	@Column (length = 60)
    private Integer id_user;
	
	@Column(length = 60)
    private Integer id_formacao;

	@Column(length = 60)
    private Date data_inicio;

	@Column(length = 60)
    private Date data_fim;
	
	@Column(length = 60)
    private String estado;
}
