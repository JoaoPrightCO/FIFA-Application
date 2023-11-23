package com.aroska.fifa.persistence.model.administration.types;

import lombok.Getter;
import lombok.Setter;

import com.aroska.fifa.constants.TableNames;

import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = TableNames.TIPOS_CONFLITO)
public class TipoConflito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 60)
    private String nome_curto;

    @Column(length = 60)
    private String nome_ext;
}