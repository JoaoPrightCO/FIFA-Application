package com.aroska.fifa.persistence.model.workflows;

import lombok.Getter;
import lombok.Setter;

import com.aroska.fifa.constants.TableNames;

import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = TableNames.WF_PARAMETROS)
public class WFParametro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //FOREIGN KEY - ADD
    @Column(length = 60)
    private Integer id_accao;
    
    @Column(length = 60)
    private String tipo_entidade;

    @Column(length = 60)
    private String campo;
    
    @Column(length = 60)
    private String valor;
}