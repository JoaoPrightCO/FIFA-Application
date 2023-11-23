package com.aroska.fifa.persistence.model.administration.core;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

import com.aroska.fifa.constants.TableNames;

import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = TableNames.AGENTES)
public class Agente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", length = 50)
    private String nome;
    
    @Column(name = "morada", length = 50)
    private String morada;
    
    //ADD FOREIGN KEY
    @Column(name = "tipoAgente", length = 2)
    private Integer tipo_agente;

    @Column(name = "nif", length = 9)
    private Integer nif;
    
    @Column(name = "nacionalidade", length = 50)
    private String nacionalidade;

    @Column(name = "dataNasc", length = 50)
    private Date data_nasc;

    @Column(name = "sexo", length = 99)
    private String sexo;

    @Column(name = "agencia", length = 50)
    private String agencia;

    @Column(name = "foto", length = 50)
    private String foto;    
    
}