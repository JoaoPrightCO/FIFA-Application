package com.aroska.fifa.persistence.model.administration.core;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

import com.aroska.fifa.constants.TableNames;

import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = TableNames.EVENTOS)
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 60)
    private String nome;

    //FOREIGN KEY - ADD
    @Column(length = 60)
    private String entidade;

    @Column(length = 60)
    private String localizacao;
    
    @Column(length = 60)
    private Date data;

    @Column(length = 60)
    private Date data_criacao;

    @Column(length = 60)
    private Date data_modificacao;

}