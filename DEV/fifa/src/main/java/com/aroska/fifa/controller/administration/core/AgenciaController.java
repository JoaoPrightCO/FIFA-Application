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
import com.aroska.fifa.persistence.daos.administration.core.AgenciaDao;
import com.aroska.fifa.persistence.model.administration.core.Agencia;
import com.aroska.fifa.requests.dtos.administration.core.AgenciaDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fifa/agencies")
public class AgenciaController {

	@Autowired
	AgenciaDao agenciaDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createAgency(@RequestBody AgenciaDto agenciaDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(agenciaDto.getValues())) {
			return new ResponseEntity<>("Not a valid agency name", HttpStatus.BAD_REQUEST);
		}
		
		if(agenciaDao.findByName(agenciaDto.getAgencia()) != null) {
			return new ResponseEntity<>("An agency with that name already exists", HttpStatus.BAD_REQUEST);
		}
		
		Agencia agency = new Agencia();
		agency.setAgencia(agenciaDto.getAgencia());
		
		try {
            agenciaDao.save(agency);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create agency, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Agency successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editAgency(@RequestBody AgenciaDto agenciaDto, @RequestParam String ida, HttpServletRequest request) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = agenciaDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(agenciaDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			agenciaDao.edit(ida, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit agencia. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing agencia", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited agencia with ID " + ida, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getAgency(@RequestParam int id) {
		Agencia agencia = agenciaDao.findById(id);

		if(agencia == null) {
			return new ResponseEntity<>("Could not find agency with ID <" + id + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found agency named " + agencia.getAgencia(), HttpStatus.OK);
	}
}
