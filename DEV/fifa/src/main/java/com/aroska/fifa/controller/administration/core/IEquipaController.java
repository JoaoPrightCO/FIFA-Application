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
import com.aroska.fifa.persistence.daos.administration.core.EquipaDao;
import com.aroska.fifa.persistence.model.administration.core.Equipa;
import com.aroska.fifa.requests.dtos.administration.core.EquipaDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fifa/teams")
public class IEquipaController {

	@Autowired
	EquipaDao equipaDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createTeam(@RequestBody EquipaDto equipaDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(equipaDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		Equipa equipa = new Equipa();
		equipa.setNome(equipaDto.getNome());
		equipa.setMorada(equipaDto.getMorada());
		equipa.setZona(equipaDto.getZona());
		equipa.setData_criacao(equipaDto.getDataCriacao());
		equipa.setNr_membros(equipaDto.getNrMembros());
		equipa.setLogotipo(equipaDto.getLogotipo());
		
		try {
            equipaDao.save(equipa);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create team, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Team successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editTeam(@RequestBody EquipaDto equipaDto, @RequestParam String ideq) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = equipaDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(equipaDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			equipaDao.edit(ideq, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit equipa. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing equipa", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited equipa with ID " + ideq, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getTeam(@RequestBody EquipaDto equipaDto) {
		Equipa equipa = equipaDao.findById(equipaDto.getId());

		if(equipa == null) {
			return new ResponseEntity<>("Could not find entity with ID <" + equipaDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found entity named " + equipa.getNome(), HttpStatus.OK);
	}
}
