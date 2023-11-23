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
import com.aroska.fifa.persistence.daos.administration.core.InstituicaoDao;
import com.aroska.fifa.persistence.model.administration.core.Instituicao;
import com.aroska.fifa.requests.dtos.administration.core.InstituicaoDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/institution")
public class IInstituicaoController {

	@Autowired
	InstituicaoDao instituicaoDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createInstitution(@RequestBody InstituicaoDto instituicaoDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(instituicaoDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		Instituicao instituicao = new Instituicao();
		instituicao.setNome(instituicaoDto.getNome());
		instituicao.setTipo(instituicaoDto.getTipo());
		
		try {
            instituicaoDao.save(instituicao);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create institution, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Institution successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editInstitution(@RequestBody InstituicaoDto instituicaoDto, @RequestParam String idins) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = instituicaoDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(instituicaoDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			instituicaoDao.edit(idins, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit instituicao. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing instituicao", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited instituicao with ID " + idins, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getInstitution(@RequestBody InstituicaoDto instituicaoDto) {
		Instituicao instituicao = instituicaoDao.findById(instituicaoDto.getId());

		if(instituicao == null) {
			return new ResponseEntity<>("Could not find institution with ID <" + instituicaoDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found institution named " + instituicao.getNome(), HttpStatus.OK);
	}
}
