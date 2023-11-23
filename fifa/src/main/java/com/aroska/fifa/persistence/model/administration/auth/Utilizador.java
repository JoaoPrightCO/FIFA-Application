package com.aroska.fifa.persistence.model.administration.auth;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

import com.aroska.fifa.constants.TableNames;

import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = TableNames.UTILIZADORES)
public class Utilizador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 60)
    private String nome;

    @Column(length = 60)
    private String username;

    @Column(length = 60)
    private String email;

    @Column(length = 200)
    private String password;
    
    @Column(length = 60)
    private String morada;

    //FOREIGN KEY - ADD
    @Column(length = 12)
    private Integer tipo_utilizador;
    
    @Column(length = 9)
    private Integer nif;

    @Column(length = 60)
    private String nacionalidade;
    
    @Column(length = 60)
    private Date data_nasc;
    
    @Column(length = 60)
    private String sexo;
    
    @Column(length = 60)
    private String foto;

}