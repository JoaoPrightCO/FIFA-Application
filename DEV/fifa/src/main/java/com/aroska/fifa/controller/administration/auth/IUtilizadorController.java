package com.aroska.fifa.controller.administration.auth;

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

import com.aroska.fifa.persistence.daos.administration.auth.UtilizadorDao;
import com.aroska.fifa.persistence.model.administration.auth.Utilizador;
import com.aroska.fifa.requests.dtos.administration.auth.UtilizadorDto;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/users")
public class IUtilizadorController {

	@Autowired
	UtilizadorDao utilizadorDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UtilizadorDto utilizadorDto){
        
		if(!StringUtil.validate(utilizadorDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		Utilizador utilizador = new Utilizador();
		utilizador.setNome(utilizadorDto.getNome());
		utilizador.setUsername(utilizadorDto.getUsername());
		utilizador.setEmail(utilizadorDto.getEmail());
		utilizador.setPassword(utilizadorDto.getPassword());
		utilizador.setMorada(utilizadorDto.getMorada());
		utilizador.setTipo_utilizador(utilizadorDto.getTipoUtilizador());
		utilizador.setNif(utilizadorDto.getNif());
		utilizador.setNacionalidade(utilizadorDto.getNacionalidade());
		utilizador.setData_nasc(utilizadorDto.getDataNasc());
		utilizador.setSexo(utilizadorDto.getSexo());
		utilizador.setFoto(utilizadorDto.getFoto());
		
		try {
            utilizadorDao.save(utilizador);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create user, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("User successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editUser(@RequestBody UtilizadorDto utilizadorDto, @RequestParam String idu, HttpServletRequest request) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = utilizadorDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(utilizadorDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			utilizadorDao.edit(idu, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit user. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing user", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited user with ID " + idu, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getUser(@RequestBody UtilizadorDto utilizadorDto) {
		Utilizador utilizador = utilizadorDao.findById(utilizadorDto.getId());

		if(utilizador == null) {
			return new ResponseEntity<>("Could not find user with ID <" + utilizadorDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found user: " + utilizador.getNome(), HttpStatus.OK);
	}
}
