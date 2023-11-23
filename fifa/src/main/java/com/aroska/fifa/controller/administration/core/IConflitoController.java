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
import com.aroska.fifa.persistence.daos.administration.core.ConflitoDao;
import com.aroska.fifa.persistence.model.administration.core.Conflito;
import com.aroska.fifa.requests.dtos.administration.core.ConflitoDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fifa/conflict")
public class IConflitoController {

	@Autowired
	ConflitoDao conflitoDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createConflict(@RequestBody ConflitoDto conflitoDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
			
		if(!StringUtil.validate(conflitoDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		Conflito conflito = new Conflito();
		conflito.setEnt1_id(conflitoDto.getEnt1Id());
		conflito.setEnt2_id(conflitoDto.getEnt2Id());
		conflito.setEntg_id(conflitoDto.getEntgId());
		conflito.setTipo_conflito(conflitoDto.getTipoConflito());
		conflito.setDescr_conflito(conflitoDto.getDescrConflito());
		conflito.setEstado(conflitoDto.getEstado());
		conflito.setData_criacao(conflitoDto.getDataCriacao());
		conflito.setData_modificacao(conflitoDto.getDataModificacao());
		conflito.setData_verdicto(conflitoDto.getDataVerdicto());
		conflito.setMotivo_recusa(conflitoDto.getMotivoRecusa());
		conflito.setDocumento(conflitoDto.getDocumento());
		
		try {
            conflitoDao.save(conflito);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create conflict, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Conflict successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editConflict(@RequestBody ConflitoDto conflitoDto, @RequestParam String idcnf) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = conflitoDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(conflitoDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			conflitoDao.edit(idcnf, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit conflito. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing conflito", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited conflito with ID " + idcnf, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getConflict(@RequestBody ConflitoDto conflitoDto) {
		Conflito conflito = conflitoDao.findById(conflitoDto.getId());
		
		if(conflito == null) {
			return new ResponseEntity<>("Could not find conflict with ID <" + conflitoDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found conflict " + conflito.getId(), HttpStatus.OK);
		
		
	}
}
