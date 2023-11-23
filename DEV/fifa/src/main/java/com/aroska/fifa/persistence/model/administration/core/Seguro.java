package com.aroska.fifa.persistence.model.administration.core;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

import com.aroska.fifa.constants.TableNames;

import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = TableNames.SEGUROS)
public class Seguro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //FOREIGN KEY - ADD
    @Column(length = 60)
    private String entidade;

    //FOREIGN KEY - ADD
    @Column(length = 60)
    private String tipo;

    @Column(length = 60)
    private Date data_inicio_contr;

    @Column(length = 60)
    private Integer pagamento_mensal;

    @Column(length = 60)
    private String descricao;
    
    @Column(length = 60)
    private Date data_criacao;
    
    @Column(length = 60)
    private Date data_modificacao;

}