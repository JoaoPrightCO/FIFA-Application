package com.aroska.fifa.controller.administration.core;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aroska.fifa.constants.TiposUtilizador;
import com.aroska.fifa.persistence.daos.administration.core.ContratoDao;
import com.aroska.fifa.persistence.model.administration.core.Contrato;
import com.aroska.fifa.requests.dtos.administration.core.ContratoDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fifa/contract")
public class ContratoController {

	@Autowired
	ContratoDao contratoDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createContract(@RequestBody ContratoDto contratoDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(contratoDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		Contrato contrato = new Contrato();
		contrato.setEnt1_id(contratoDto.getEnt1Id());
		contrato.setEnt2_id(contratoDto.getEnt2Id());
		contrato.setTipo_contrato(contratoDto.getTipoContrato());
		contrato.setDescr_contrato(contratoDto.getDescrContrato());
		contrato.setEstado(contratoDto.getEstado());
		contrato.setData_criacao(contratoDto.getDataCriacao());
		contrato.setData_modificacao(contratoDto.getDataModificacao());
		contrato.setMotivo(contratoDto.getMotivo());
		contrato.setDocumento(contratoDto.getDocumento());
		
		try {
            contratoDao.save(contrato);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create contract, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Contract successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editContract(@RequestBody ContratoDto contratoDto, @RequestParam String idct) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = contratoDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(contratoDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			contratoDao.edit(idct, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit contrato. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing contrato", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited contrato with ID " + idct, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getConflict(@RequestBody ContratoDto contratoDto) {
		Contrato contrato = contratoDao.findById(contratoDto.getId());
		
		if(contrato == null) {
			return new ResponseEntity<>("Could not find contract with ID <" + contratoDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found contract " + contrato.getId(), HttpStatus.OK);
		
		
	}
}
