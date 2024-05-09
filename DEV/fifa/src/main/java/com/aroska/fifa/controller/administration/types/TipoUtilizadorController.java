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
import com.aroska.fifa.persistence.daos.administration.types.TipoUtilizadorDao;
import com.aroska.fifa.persistence.model.administration.types.TipoUtilizador;
import com.aroska.fifa.requests.dtos.administration.types.TipoUtilizadorDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/userType")
public class TipoUtilizadorController {

	@Autowired
	TipoUtilizadorDao tipoUtilizadorDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createUserType(@RequestBody TipoUtilizadorDto tipoUtilizadorDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(tipoUtilizadorDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		TipoUtilizador tipoUtilizador = new TipoUtilizador();
		tipoUtilizador.setNome_curto(tipoUtilizadorDto.getNome_curto());
		tipoUtilizador.setNome_ext(tipoUtilizadorDto.getNome_ext());
		
		try {
            tipoUtilizadorDao.save(tipoUtilizador);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create user type, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("User type successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editUserType(@RequestBody TipoUtilizadorDto tipoUtilizadorDto, @RequestParam String idtpu) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = tipoUtilizadorDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(tipoUtilizadorDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			tipoUtilizadorDao.edit(idtpu, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit tipo(utilizador). Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing tipo(utilizador)", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited tipo(utilizador) with ID " + idtpu, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getUserType(@RequestBody TipoUtilizadorDto tipoUtilizadorDto) {
		TipoUtilizador tipoUtilizador = tipoUtilizadorDao.findById(tipoUtilizadorDto.getId());

		if(tipoUtilizador == null) {
			return new ResponseEntity<>("Could not find user type with ID <" + tipoUtilizadorDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found user type: " + tipoUtilizador.getNome_curto(), HttpStatus.OK);
	}
}
