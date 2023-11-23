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
import com.aroska.fifa.persistence.daos.administration.core.AgenteDao;
import com.aroska.fifa.persistence.model.administration.core.Agente;
import com.aroska.fifa.requests.dtos.administration.core.AgenteDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fifa/agents")
public class IAgenteController {

	@Autowired
	AgenteDao agenteDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createAgent(@RequestBody AgenteDto agenteDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(agenteDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		if(agenteDao.findByNif(agenteDto.getNif()) != null ) {
			return new ResponseEntity<>("Agent NIF is already taken", HttpStatus.BAD_REQUEST);
		}
		
		Agente agente = new Agente();
		agente.setNome(agenteDto.getNome());
		agente.setMorada(agenteDto.getMorada());
		agente.setTipo_agente(agenteDto.getTipoAgente());
		agente.setNif(agenteDto.getNif());
		agente.setNacionalidade(agenteDto.getNacionalidade());
		agente.setData_nasc(agenteDto.getDataNasc());
		agente.setSexo(agenteDto.getSexo());
		agente.setAgencia(agenteDto.getAgencia());
		agente.setFoto(agenteDto.getFoto());
		
		try {
            agenteDao.save(agente);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create agent, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Agent successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editAgent(@RequestBody AgenteDto agenteDto, @RequestParam String idag) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = agenteDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(agenteDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			agenteDao.edit(idag, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit agente. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing agente", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited agente with ID " + idag, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getAgent(@RequestBody AgenteDto agenteDto) {
		Agente agente = agenteDao.findById(agenteDto.getId());

		if(agente == null) {
			return new ResponseEntity<>("Could not find agent with ID <" + agenteDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Found agent named " + agente.getNome(), HttpStatus.OK);
	}
}
