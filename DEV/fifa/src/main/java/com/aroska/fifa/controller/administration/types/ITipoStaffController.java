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
import com.aroska.fifa.persistence.daos.administration.types.TipoStaffDao;
import com.aroska.fifa.persistence.model.administration.types.TipoStaff;
import com.aroska.fifa.requests.dtos.administration.types.TipoStaffDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/staffType")
public class ITipoStaffController {

	@Autowired
	TipoStaffDao tipoStaffDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createStaffType(@RequestBody TipoStaffDto tipoStaffDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(tipoStaffDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		TipoStaff tipoStaff = new TipoStaff();
		tipoStaff.setNome_curto(tipoStaffDto.getNome_curto());
		tipoStaff.setNome_ext(tipoStaffDto.getNome_ext());
		
		try {
            tipoStaffDao.save(tipoStaff);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create staff type, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Staff type successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editStaffType(@RequestBody TipoStaffDto tipoStaffDto, @RequestParam String idtpst) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = tipoStaffDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(tipoStaffDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			tipoStaffDao.edit(idtpst, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit tipo(staff). Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing tipo(staff)", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited tipo(staff) with ID " + idtpst, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getStaffType(@RequestBody TipoStaffDto tipoStaffDto) {
		TipoStaff tipoStaff = tipoStaffDao.findById(tipoStaffDto.getId());

		if(tipoStaff == null) {
			return new ResponseEntity<>("Could not find staff type with ID <" + tipoStaffDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found staff type: " + tipoStaff.getNome_curto(), HttpStatus.OK);
	}
}
