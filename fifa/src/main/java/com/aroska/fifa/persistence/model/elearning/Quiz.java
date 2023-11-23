package com.aroska.fifa.persistence.model.elearning;

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
@Table(name = TableNames.QUIZZES)
public class Quiz {

	//TODO
	//FOREIGN KEYS - ADD
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(length = 60)
    private Integer id_sessao;
	
	@Column(length = 60)
    private String descricao;
}
