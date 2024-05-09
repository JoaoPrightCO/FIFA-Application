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
import com.aroska.fifa.persistence.daos.administration.core.EntidadeDao;
import com.aroska.fifa.persistence.model.administration.core.Entidade;
import com.aroska.fifa.requests.dtos.administration.core.EntidadeDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fifa/entity")
public class EntidadeController {

	@Autowired
	EntidadeDao entidadeDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createEntity(@RequestBody EntidadeDto entidadeDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(entidadeDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		Entidade entidade = new Entidade();
		entidade.setNome(entidadeDto.getNome());
		entidade.setMorada(entidadeDto.getMorada());
		entidade.setTipo_entidade(entidadeDto.getTipoEntidade());
		entidade.setData_fundacao(entidadeDto.getDataFundacao());
		entidade.setLogotipo(entidadeDto.getLogotipo());
		
		try {
            entidadeDao.save(entidade);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create entity, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Entity successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editEntity(@RequestBody EntidadeDto entidadeDto, @RequestParam String ident) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = entidadeDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(entidadeDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			entidadeDao.edit(ident, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit entidade. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing entidade", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited entidade with ID " + ident, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getEntity(@RequestBody EntidadeDto entidadeDto) {
		Entidade entidade = entidadeDao.findById(entidadeDto.getId());

		if(entidade == null) {
			return new ResponseEntity<>("Could not find entity with ID <" + entidadeDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found entity named " + entidade.getNome(), HttpStatus.OK);
	}
}
