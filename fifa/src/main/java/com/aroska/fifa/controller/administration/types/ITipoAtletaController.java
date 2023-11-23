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
import com.aroska.fifa.persistence.daos.administration.types.TipoAtletaDao;
import com.aroska.fifa.persistence.model.administration.types.TipoAtleta;
import com.aroska.fifa.requests.dtos.administration.types.TipoAtletaDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/athleteType")
public class ITipoAtletaController {

	@Autowired
	TipoAtletaDao tipoAtletaDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createAthleteType(@RequestBody TipoAtletaDto tipoAtletaDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(tipoAtletaDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		TipoAtleta tipoAtleta = new TipoAtleta();
		tipoAtleta.setNome_curto(tipoAtletaDto.getNome_curto());
		tipoAtleta.setNome_ext(tipoAtletaDto.getNome_ext());
		
		try {
            tipoAtletaDao.save(tipoAtleta);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create athlete type, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Athlete type successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editAthleteType(@RequestBody TipoAtletaDto tipoAtletaDto, @RequestParam String idtpat) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = tipoAtletaDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(tipoAtletaDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			tipoAtletaDao.edit(idtpat, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit tipo(atleta). Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing tipo(atleta)", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited tipo(atleta) with ID " + idtpat, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getAthleteType(@RequestBody TipoAtletaDto tipoAtletaDto) {
		TipoAtleta tipoAtleta = tipoAtletaDao.findById(tipoAtletaDto.getId());

		if(tipoAtleta == null) {
			return new ResponseEntity<>("Could not find athlete type with ID <" + tipoAtletaDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found athlete type: " + tipoAtleta.getNome_curto(), HttpStatus.OK);
	}
}
