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
import com.aroska.fifa.persistence.daos.administration.core.RelacaoJuridicaDao;
import com.aroska.fifa.persistence.model.administration.core.RelacaoJuridica;
import com.aroska.fifa.requests.dtos.administration.core.RelacaoJuridicaDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/relacaojuridica")
public class IRelacaoJuridicaController {

	@Autowired
	RelacaoJuridicaDao relacaoJuridicaDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createJuridicalRelationship(@RequestBody RelacaoJuridicaDto relacaoJuridicaDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(relacaoJuridicaDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		RelacaoJuridica relacaoJuridica = new RelacaoJuridica();
		relacaoJuridica.setEntidade(relacaoJuridicaDto.getEntidade());
		relacaoJuridica.setInstituicao(relacaoJuridicaDto.getInstituicao());
		relacaoJuridica.setRelacao(relacaoJuridicaDto.getRelacao());
		relacaoJuridica.setData_criacao(relacaoJuridicaDto.getDataCriacao());
		relacaoJuridica.setData_modificacao(relacaoJuridicaDto.getDataModificacao());
		
		try {
            relacaoJuridicaDao.save(relacaoJuridica);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create juridical relationship, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Juridical relationship successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editJuridicalRelationship(@RequestBody RelacaoJuridicaDto relacaoJuridicaDto, @RequestParam String idrj) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = relacaoJuridicaDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(relacaoJuridicaDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			relacaoJuridicaDao.edit(idrj, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit relacao(juridica). Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing relacao(juridica)", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited relacao(juridica) with ID " + idrj, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getJuridicalRelationship(@RequestBody RelacaoJuridicaDto relacaoJuridicaDto) {
		RelacaoJuridica relacaoJuridica = relacaoJuridicaDao.findById(relacaoJuridicaDto.getId());

		if(relacaoJuridica == null) {
			return new ResponseEntity<>("Could not find juridical relationship with ID <" + relacaoJuridicaDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found entity " + relacaoJuridica.getEntidade() + " with relationship: " + relacaoJuridica.getRelacao(), HttpStatus.OK);
	}
}
