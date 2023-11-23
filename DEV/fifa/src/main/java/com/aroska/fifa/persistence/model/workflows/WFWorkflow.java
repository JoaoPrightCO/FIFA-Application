package com.aroska.fifa.persistence.model.workflows;

import lombok.Getter;
import lombok.Setter;

import com.aroska.fifa.constants.TableNames;

import jakarta.persistence.*;

@Setter
@Getter
@Entity
@Table(name = TableNames.WF_WORKFLOWS)
public class WFWorkflow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //FOREIGN KEY - ADD
    @Column(length = 60)
    private Integer id_user;
    
    @Column(length = 60)
    private String nome;
}