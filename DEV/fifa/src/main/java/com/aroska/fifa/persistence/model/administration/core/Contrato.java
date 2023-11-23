package com.aroska.fifa.persistence.model.administration.core;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

import com.aroska.fifa.constants.TableNames;

import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = TableNames.CONTRATOS)
public class Contrato {

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
    private String tipo_contrato;

    @Column(length = 60)
    private String descr_contrato;

    @Column(length = 60)
    private String estado;

    @Column(length = 60)
    private Date data_criacao;

    @Column(length = 60)
    private Date data_modificacao;

    @Column(length = 60)
    private String motivo;

    @Column(length = 60)
    private String documento;

}