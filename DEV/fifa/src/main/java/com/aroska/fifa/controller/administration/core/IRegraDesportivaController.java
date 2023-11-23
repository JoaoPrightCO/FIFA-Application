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
import com.aroska.fifa.persistence.daos.administration.core.RegraDesportivaDao;
import com.aroska.fifa.persistence.model.administration.core.RegraDesportiva;
import com.aroska.fifa.requests.dtos.administration.core.RegraDesportivaDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/regradesportiva")
public class IRegraDesportivaController {

	@Autowired
	RegraDesportivaDao regraDesportivaDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createSportsRule(@RequestBody RegraDesportivaDto regraDesportivaDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(regraDesportivaDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		RegraDesportiva regraDesportiva = new RegraDesportiva();
		regraDesportiva.setZona(regraDesportivaDto.getZona());
		regraDesportiva.setTipo(regraDesportivaDto.getTipo());
		regraDesportiva.setDescricao(regraDesportivaDto.getDescricao());
		regraDesportiva.setData_criacao(regraDesportivaDto.getDataCriacao());
		regraDesportiva.setData_modificacao(regraDesportivaDto.getDataModificacao());
		
		try {
            regraDesportivaDao.save(regraDesportiva);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create sports rule, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Sports rule successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editSportsRule(@RequestBody RegraDesportivaDto regraDesportivaDto, @RequestParam String idrd) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = regraDesportivaDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(regraDesportivaDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			regraDesportivaDao.edit(idrd, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit regra desportiva. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing regra desportiva", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited regra desportiva with ID " + idrd, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getSportsRule(@RequestBody RegraDesportivaDto regraDesportivaDto) {
		RegraDesportiva regraDesportiva = regraDesportivaDao.findById(regraDesportivaDto.getId());

		if(regraDesportiva == null) {
			return new ResponseEntity<>("Could not find sports rule with ID <" + regraDesportivaDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found sports rule with description: " + regraDesportiva.getDescricao(), HttpStatus.OK);
	}
}
