package com.aroska.fifa.persistence.model.administration.core;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

import com.aroska.fifa.constants.TableNames;

import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = TableNames.ENTIDADES)
public class Entidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 60)
    private String nome;

    @Column(length = 60)
    private String morada;

    //FOREIGN KEY - ADD
    @Column(length = 60)
    private String tipo_entidade;

    @Column(length = 60)
    private Date data_fundacao;

    @Column(length = 60)
    private String logotipo;

}