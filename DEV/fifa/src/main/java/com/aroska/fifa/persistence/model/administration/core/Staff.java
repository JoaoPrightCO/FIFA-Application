package com.aroska.fifa.persistence.model.administration.core;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

import com.aroska.fifa.constants.TableNames;

import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = TableNames.STAFF)
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 60)
    private String nome;

    @Column(length = 60)
    private String morada;

    //FOREIGN KEY - ADD
    @Column(length = 60)
    private String tipo_staff;

    @Column(length = 9)
    private Integer nif;

    @Column(length = 60)
    private String nacionalidade;
    
    @Column(length = 60)
    private Date data_nasc;
    
    @Column(length = 60)
    private String sexo;

    //FOREIGN KEY - ADD
    @Column(length = 60)
    private String cargo;
    
    @Column(length = 60)
    private String foto;

}