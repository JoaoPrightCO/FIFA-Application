package com.aroska.fifa.controller.administration.types;

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
import com.aroska.fifa.persistence.daos.administration.types.TipoConflitoDao;
import com.aroska.fifa.persistence.model.administration.types.TipoConflito;
import com.aroska.fifa.requests.dtos.administration.types.TipoConflitoDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/conflictType")
public class TipoConflitoController {

	@Autowired
	TipoConflitoDao tipoConflitoDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createConflictType(@RequestBody TipoConflitoDto tipoConflitoDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(tipoConflitoDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		TipoConflito tipoConflito = new TipoConflito();
		tipoConflito.setNome_curto(tipoConflitoDto.getNome_curto());
		tipoConflito.setNome_ext(tipoConflitoDto.getNome_ext());
		
		try {
            tipoConflitoDao.save(tipoConflito);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create conflict type, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Conflict type successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editConflictType(@RequestBody TipoConflitoDto tipoConflitoDto, @RequestParam String idtpcf) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = tipoConflitoDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(tipoConflitoDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			tipoConflitoDao.edit(idtpcf, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit tipo(conflito). Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing tipo(conflito)", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited tipo(conflito) with ID " + idtpcf, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getConflictType(@RequestBody TipoConflitoDto tipoConflitoDto) {
		TipoConflito tipoConflito = tipoConflitoDao.findById(tipoConflitoDto.getId());

		if(tipoConflito == null) {
			return new ResponseEntity<>("Could not find conflict type with ID <" + tipoConflitoDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found conflict type: " + tipoConflito.getNome_curto(), HttpStatus.OK);
	}
}
