package com.aroska.fifa.persistence.model.administration.core;

import lombok.Getter;
import lombok.Setter;

import com.aroska.fifa.constants.TableNames;

import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = TableNames.VIDEOS)
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //FOREIGN KEY - ADD
    @Column(length = 60)
    private Integer id_jogo;

    //FOREIGN KEY - ADD
    @Column(length = 60)
    private Integer id_camara;

    @Column(length = 60)
    private String video;
    
    @Column(length = 60)
    private Integer duracao;

}