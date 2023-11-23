package com.aroska.fifa.controller.administration.core;

import java.util.ArrayList;
import java.util.List;

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
import com.aroska.fifa.persistence.daos.administration.core.AtletaDao;
import com.aroska.fifa.persistence.model.administration.core.Atleta;
import com.aroska.fifa.requests.dtos.administration.core.AtletaDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fifa/athlete")
public class IAtletaController {

	@Autowired
	AtletaDao atletaDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createAthlete(@RequestBody AtletaDto atletaDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(atletaDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		Atleta atleta = new Atleta();
		atleta.setNome(atletaDto.getNome());
		atleta.setMorada(atletaDto.getMorada());
		atleta.setTipo_atleta(atletaDto.getTipoAtleta());
		atleta.setNif(atletaDto.getNif());
		atleta.setNacionalidade(atletaDto.getNacionalidade());
		atleta.setData_nasc(atletaDto.getData_nasc());
		atleta.setSexo(atletaDto.getSexo());
		atleta.setId_equipa(atletaDto.getIdEquipa());
		atleta.setPosicao(atletaDto.getPosicao());
		atleta.setPeso(atletaDto.getPeso());
		atleta.setAltura(atletaDto.getAltura());
		atleta.setPosse_bola(atletaDto.getPosseBola());
		atleta.setGolos_total(atletaDto.getGolosTotal());
		atleta.setGolos_media(atletaDto.getGolosMedia());
		atleta.setSubstituicoes(atletaDto.getSubstituicoes());
		atleta.setFoto(atletaDto.getFoto());
		
		try {
            atletaDao.save(atleta);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create athlete, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Athlete successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editAthlete(@RequestBody AtletaDto atletaDto, @RequestParam String idat) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = atletaDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(atletaDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			atletaDao.edit(idat, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit atleta. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing atleta", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited atleta with ID " + idat, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getAthlete(@RequestBody AtletaDto atletaDto) {
		Atleta atleta = atletaDao.findById(atletaDto.getId());
		
		if(atleta == null) {
			return new ResponseEntity<>("Could not find athlete with ID <" + atletaDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found athlete " + atleta.getNome(), HttpStatus.OK);
		
		
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<String> getAllAthletes() {
		List<Atleta> athletesList = atletaDao.getAllAthletes();
		
		String userString = "";
		
		for(int i = 0; i<athletesList.size(); i++) {
			Atleta u = athletesList.get(i);
			userString+=u.getNome() + "\n";
		}
		
		return new ResponseEntity<>("Athletes List: \n" + userString, HttpStatus.OK);
	}
}
