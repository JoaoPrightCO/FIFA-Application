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
import com.aroska.fifa.persistence.daos.administration.core.FedRespExtraContrDao;
import com.aroska.fifa.persistence.model.administration.core.FedRespExtraContr;
import com.aroska.fifa.requests.dtos.administration.core.FedRespExtraContrDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/fedrespextracontr")
public class FedRespExtrContrController {

	@Autowired
	FedRespExtraContrDao fedRespExtraContrDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createFedRespExtraContr(@RequestBody FedRespExtraContrDto fedRespExtraContrDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(fedRespExtraContrDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		FedRespExtraContr fedRespExtraContr = new FedRespExtraContr();
		fedRespExtraContr.setEntidade(fedRespExtraContrDto.getEntidade());
		fedRespExtraContr.setDescricao(fedRespExtraContrDto.getDescricao());
		
		try {
            fedRespExtraContrDao.save(fedRespExtraContr);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create extra-contractual responsibility, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Contractual responsibility successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editFedRespExtraContr(@RequestBody FedRespExtraContrDto fedRespExtraContrDto, @RequestParam String idfrxc) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = fedRespExtraContrDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(fedRespExtraContrDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			fedRespExtraContrDao.edit(idfrxc, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit resp extra contratual. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing resp extra contratual", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited resp extra contratual with ID " + idfrxc, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getFedRespExtraContr(@RequestBody FedRespExtraContrDto fedRespExtraContrDto) {
		FedRespExtraContr fedRespExtraContr = fedRespExtraContrDao.findById(fedRespExtraContrDto.getId());

		if(fedRespExtraContr == null) {
			return new ResponseEntity<>("Could not find contractual responsibility with ID <" + fedRespExtraContrDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found contractual responsibility named " + fedRespExtraContr.getEntidade(), HttpStatus.OK);
	}
}
