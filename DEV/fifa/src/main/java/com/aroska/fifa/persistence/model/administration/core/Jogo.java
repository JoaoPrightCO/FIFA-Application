package com.aroska.fifa.persistence.model.administration.core;

import lombok.Getter;
import lombok.Setter;

import com.aroska.fifa.constants.TableNames;

import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = TableNames.JOGOS)
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //FOREIGN KEY - ADD
    @Column(length = 60)
    private Integer id_equipa1;

    //FOREIGN KEY - ADD
    @Column(length = 60)
    private Integer id_equipa2;
    
    //FOREIGN KEY - ADD
    @Column(length = 60)
    private Integer id_evento;
    
    @Column(length = 60)
    private String localizacao;

    @Column(length = 60)
    private int dur_prolongamento;

    @Column(length = 60)
    private String equipa_vencedora;

    @Column(length = 60)
    private Integer golos_equipa1;

    @Column(length = 60)
    private Integer golos_equipa2;

    @Column(length = 60)
    private Integer nr_substituicoes;

    @Column(length = 60)
    private Integer nr_faltas;

    @Column(length = 60)
    private Integer posse_equipa1;

    @Column(length = 60)
    private Integer posse_equipa2;

    @Column(length = 60)
    private String est_metereologico;

}