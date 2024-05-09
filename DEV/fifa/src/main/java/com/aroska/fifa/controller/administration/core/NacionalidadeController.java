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
import com.aroska.fifa.persistence.daos.administration.core.NacionalidadeDao;
import com.aroska.fifa.persistence.model.administration.core.Nacionalidade;
import com.aroska.fifa.requests.dtos.administration.core.NacionalidadeDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/nationalities")
public class NacionalidadeController {

	@Autowired
	NacionalidadeDao nacionalidadeDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createNationality(@RequestBody NacionalidadeDto nacionalidadeDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(nacionalidadeDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		Nacionalidade nacionalidade = new Nacionalidade();
		nacionalidade.setNome_curto(nacionalidadeDto.getNomeCurto());
		nacionalidade.setNome_ext(nacionalidadeDto.getNomeExt());
		
		try {
            nacionalidadeDao.save(nacionalidade);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create nationality, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Nationality successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editNationality(@RequestBody NacionalidadeDto nacionalidadeDto, @RequestParam String idnac) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = nacionalidadeDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(nacionalidadeDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			nacionalidadeDao.edit(idnac, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit nacionalidade. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing nacionalidade", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited nacionalidade with ID " + idnac, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getNationality(@RequestBody NacionalidadeDto nacionalidadeDto) {
		Nacionalidade nacionalidade = nacionalidadeDao.findById(nacionalidadeDto.getId());

		if(nacionalidade == null) {
			return new ResponseEntity<>("Could not find nationality with ID <" + nacionalidadeDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found nationality " + nacionalidade.getNome_curto(), HttpStatus.OK);
	}
}
