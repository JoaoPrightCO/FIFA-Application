package com.aroska.fifa.persistence.model.administration.core;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

import com.aroska.fifa.constants.TableNames;

import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = TableNames.REGRAS_DESPORTIVAS)
public class RegraDesportiva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //FOREIGN KEY - ADD
    @Column(length = 60)
    private String zona;

    @Column(length = 60)
    private String tipo;

    @Column(length = 60)
    private String descricao;

    @Column(length = 60)
    private Date data_criacao;

    @Column(length = 60)
    private Date data_modificacao;

}