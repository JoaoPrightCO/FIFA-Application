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
import com.aroska.fifa.persistence.daos.administration.core.ZonaDao;
import com.aroska.fifa.persistence.model.administration.core.Zona;
import com.aroska.fifa.requests.dtos.administration.core.ZonaDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/zones")
public class ZonaController {

	@Autowired
	ZonaDao zonaDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createZone(@RequestBody ZonaDto zonaDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(zonaDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		Zona zona = new Zona();
		zona.setZona(zonaDto.getZona());
		
		try {
            zonaDao.save(zona);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create zone, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Zone successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editZone(@RequestBody ZonaDto zonaDto, @RequestParam String idz) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = zonaDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(zonaDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			zonaDao.edit(idz, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit zona. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing zona", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited zona with ID " + idz, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getZone(@RequestBody ZonaDto zonaDto) {
		Zona zona = zonaDao.findById(zonaDto.getId());

		if(zona == null) {
			return new ResponseEntity<>("Could not find zone with ID <" + zonaDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found zone: " + zonaDto.getZona(), HttpStatus.OK);
	}
}
