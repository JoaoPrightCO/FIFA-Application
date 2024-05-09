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
import com.aroska.fifa.persistence.daos.administration.core.TreinadorDao;
import com.aroska.fifa.persistence.model.administration.core.Treinador;
import com.aroska.fifa.requests.dtos.administration.core.TreinadorDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/coaches")
public class TreinadorController {

	@Autowired
	TreinadorDao treinadorDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createCoach(@RequestBody TreinadorDto treinadorDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(treinadorDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		Treinador treinador = new Treinador();
		treinador.setNome(treinadorDto.getNome());
		treinador.setMorada(treinadorDto.getMorada());
		treinador.setNif(treinadorDto.getNif());
		treinador.setNacionalidade(treinadorDto.getNacionalidade());
		treinador.setData_nasc(treinadorDto.getDataNasc());
		treinador.setSexo(treinadorDto.getSexo());
		treinador.setId_equipa(treinadorDto.getIdEquipa());
		treinador.setCertificacao(treinadorDto.getCertificacao());
		treinador.setFoto(treinadorDto.getFoto());
		
		try {
            treinadorDao.save(treinador);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create coach, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Coach successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editCoach(@RequestBody TreinadorDto treinadorDto, @RequestParam String idtr) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = treinadorDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(treinadorDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			treinadorDao.edit(idtr, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit treinador. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing treinador", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited treinador with ID " + idtr, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getCoach(@RequestBody TreinadorDto treinadorDto) {
		Treinador treinador = treinadorDao.findById(treinadorDto.getId());

		if(treinador == null) {
			return new ResponseEntity<>("Could not find coach with ID <" + treinadorDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found coach: " + treinador.getNome(), HttpStatus.OK);
	}
}
