package com.aroska.fifa.persistence.model.administration.core;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

import com.aroska.fifa.constants.TableNames;

import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = TableNames.ATLETAS)
public class Atleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String nome;
    
    @Column(length = 50)
    private String morada;
    
    //ADD FOREIGN KEY
    @Column(length = 2)
    private String tipo_atleta;

    @Column(length = 9)
    private Integer nif;
    
    @Column(length = 50)
    private String nacionalidade;

    @Column(length = 50)
    private Date data_nasc;

    @Column(length = 50)
    private String sexo;

    //ADD FOREIGN KEY
    @Column(length = 50)
    private Integer id_equipa;

    @Column(length = 50)
    private String posicao;

    @Column(length = 50)
    private Integer peso;

    @Column(length = 50)
    private Integer altura;

    @Column(length = 50)
    private int posse_bola;

    @Column(length = 50)
    private Integer golos_total;

    @Column(length = 50)
    private Integer golos_media;

    @Column(length = 50)
    private Integer substituicoes;

    @Column(length = 50)
    private String foto;
    
    private int pontuacao;
    
    public int getPontuacao() {
    	return pontuacao;
    }
    
    public void setPontuacao(int score) {
    	pontuacao = score;
    }
    
}