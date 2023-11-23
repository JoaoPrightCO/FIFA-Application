package com.aroska.fifa.persistence.model.administration.types;

import lombok.Getter;
import lombok.Setter;

import com.aroska.fifa.constants.TableNames;

import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = TableNames.TIPOS_SEGURO)
public class TipoSeguro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 60)
    private String tipo_seguro;
}