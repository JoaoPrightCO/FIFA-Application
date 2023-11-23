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
import com.aroska.fifa.persistence.daos.administration.core.CargoDao;
import com.aroska.fifa.persistence.model.administration.core.Cargo;
import com.aroska.fifa.requests.dtos.administration.core.CargoDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fifa/cargo")
public class ICargoController {

	@Autowired
	CargoDao cargoDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createCargo(@RequestBody CargoDto cargoDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(cargoDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		Cargo cargo = new Cargo();
		cargo.setCargo(cargoDto.getCargo());
		
		try {
            cargoDao.save(cargo);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create role, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Role successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editCargo(@RequestBody CargoDto cargoDto, @RequestParam String idcrg) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = cargoDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(cargoDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			cargoDao.edit(idcrg, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit cargo. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing cargo", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited cargo with ID " + idcrg, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getCargo(@RequestBody CargoDto cargoDto) {
		Cargo cargo = cargoDao.findById(cargoDto.getId());
		
		if(cargo == null) {
			return new ResponseEntity<>("Could not find role with ID <" + cargoDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found role " + cargo.getCargo(), HttpStatus.OK);
		
		
	}
}
