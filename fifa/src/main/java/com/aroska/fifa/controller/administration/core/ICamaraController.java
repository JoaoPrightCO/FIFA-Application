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
import com.aroska.fifa.persistence.daos.administration.core.CamaraDao;
import com.aroska.fifa.persistence.model.administration.core.Camara;
import com.aroska.fifa.requests.dtos.administration.core.CamaraDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fifa/cam")
public class ICamaraController {

	@Autowired
	CamaraDao camaraDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createCamera(@RequestBody CamaraDto camaraDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(camaraDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		Camara cam = new Camara();
		cam.setLocalizacao(camaraDto.getLocalizacao());
		cam.setLocalizacao_cam(camaraDto.getLocalizacaoCam());
		
		try {
            camaraDao.save(cam);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create camera, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Camera successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editCamera(@RequestBody CamaraDto camaraDto, @RequestParam String idcm) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = camaraDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(camaraDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			camaraDao.edit(idcm, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit camara. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing camara", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited camara with ID " + idcm, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getCamera(@RequestBody CamaraDto camaraDto) {
		Camara cam = camaraDao.findById(camaraDto.getId());
		
		if(cam == null) {
			return new ResponseEntity<>("Could not find camera with ID <" + camaraDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found camera " + cam.getId(), HttpStatus.OK);
		
		
	}
}
