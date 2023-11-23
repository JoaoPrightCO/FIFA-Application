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
import com.aroska.fifa.persistence.daos.administration.types.TipoSeguroDao;
import com.aroska.fifa.persistence.model.administration.types.TipoSeguro;
import com.aroska.fifa.requests.dtos.administration.types.TipoSeguroDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/insuranceType")
public class ITipoSeguroController {

	@Autowired
	TipoSeguroDao tipoSeguroDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createInsuranceType(@RequestBody TipoSeguroDto tipoSeguroDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(tipoSeguroDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		TipoSeguro tipoSeguro = new TipoSeguro();
		tipoSeguro.setTipo_seguro(tipoSeguroDto.getTipo_seguro());
		
		try {
            tipoSeguroDao.save(tipoSeguro);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create insurance type, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Insurance type successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editInsuranceType(@RequestBody TipoSeguroDto tipoSeguroDto, @RequestParam String idtpsg) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = tipoSeguroDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(tipoSeguroDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			tipoSeguroDao.edit(idtpsg, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit tipo(seguro). Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing tipo(seguro)", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited tipo(seguro) with ID " + idtpsg, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getInsuranceType(@RequestBody TipoSeguroDto tipoSeguroDto) {
		TipoSeguro tipoSeguro = tipoSeguroDao.findById(tipoSeguroDto.getId());

		if(tipoSeguro == null) {
			return new ResponseEntity<>("Could not find insurance type with ID <" + tipoSeguroDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found insurance type: " + tipoSeguro.getTipo_seguro(), HttpStatus.OK);
	}
}
