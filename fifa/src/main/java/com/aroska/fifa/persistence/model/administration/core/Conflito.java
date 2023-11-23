package com.aroska.fifa.persistence.model.administration.core;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

import com.aroska.fifa.constants.TableNames;

import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = TableNames.CONFLITOS)
public class Conflito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //FOREIGN KEY - ADD
    @Column(length = 60)
    private Integer ent1_id;

    //FOREIGN KEY - ADD
    @Column(length = 60)
    private Integer ent2_id;

    //FOREIGN KEY - ADD
    @Column(length = 60)
    private Integer entg_id;

    //FOREIGN KEY - ADD
    @Column(length = 60)
    private String tipo_conflito;

    @Column(length = 60)
    private String descr_conflito;

    @Column(length = 60)
    private String estado;

    @Column(length = 60)
    private Date data_criacao;

    @Column(length = 60)
    private Date data_modificacao;

    @Column(length = 60)
    private Date data_verdicto;

    @Column(length = 60)
    private String motivo_recusa;

    @Column(length = 60)
    private String documento;

}