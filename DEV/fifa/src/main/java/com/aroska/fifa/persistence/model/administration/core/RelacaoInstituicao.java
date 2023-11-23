package com.aroska.fifa.persistence.model.administration.core;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

import com.aroska.fifa.constants.TableNames;

import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = TableNames.RELACOES_INSTITUICOES)
public class RelacaoInstituicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //FOREIGN KEY - ADD
    @Column(length = 60)
    private String entidade;

    //FOREIGN KEY - ADD
    @Column(length = 60)
    private String instituicao;

    @Column(length = 60)
    private String relacao;

    @Column(length = 60)
    private Date data_criacao;

    @Column(length = 60)
    private Date data_modificacao;
}