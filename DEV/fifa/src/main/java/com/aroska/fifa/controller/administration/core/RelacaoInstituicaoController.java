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
import com.aroska.fifa.persistence.daos.administration.core.RelacaoInstituicaoDao;
import com.aroska.fifa.persistence.model.administration.core.RelacaoInstituicao;
import com.aroska.fifa.requests.dtos.administration.core.RelacaoInstituicaoDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/relacaoinstituicao")
public class RelacaoInstituicaoController {

	@Autowired
	RelacaoInstituicaoDao relacaoInstituicaoDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createcreateInstitutionalRelationship(@RequestBody RelacaoInstituicaoDto relacaoInstituicaoDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(relacaoInstituicaoDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		RelacaoInstituicao relacaoInstituicao = new RelacaoInstituicao();
		relacaoInstituicao.setEntidade(relacaoInstituicaoDto.getEntidade());
		relacaoInstituicao.setInstituicao(relacaoInstituicaoDto.getInstituicao());
		relacaoInstituicao.setRelacao(relacaoInstituicaoDto.getRelacao());
		relacaoInstituicao.setData_criacao(relacaoInstituicaoDto.getDataCriacao());
		relacaoInstituicao.setData_modificacao(relacaoInstituicaoDto.getDataModificacao());
		
		try {
            relacaoInstituicaoDao.save(relacaoInstituicao);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create institution relationship, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Institutional relationship successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editInstitutionalRelationship(@RequestBody RelacaoInstituicaoDto relacaoInstituicaoDto, @RequestParam String idri) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = relacaoInstituicaoDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(relacaoInstituicaoDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			relacaoInstituicaoDao.edit(idri, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit relacao(instituicao). Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing relacao(instituicao)", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited relacao(instituicao) with ID " + idri, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getInstitutionalRelationship(@RequestBody RelacaoInstituicaoDto relacaoInstituicaoDto) {
		RelacaoInstituicao relacaoInstituicao = relacaoInstituicaoDao.findById(relacaoInstituicaoDto.getId());

		if(relacaoInstituicao == null) {
			return new ResponseEntity<>("Could not find institutional relationship with ID <" + relacaoInstituicaoDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found entity " + relacaoInstituicao.getEntidade() + " with relationship: " + relacaoInstituicao.getRelacao(), HttpStatus.OK);
	}
}
