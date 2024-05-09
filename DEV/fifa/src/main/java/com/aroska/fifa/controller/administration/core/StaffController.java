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
import com.aroska.fifa.persistence.daos.administration.core.StaffDao;
import com.aroska.fifa.persistence.model.administration.core.Staff;
import com.aroska.fifa.requests.dtos.administration.core.StaffDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/staff")
public class StaffController {

	@Autowired
	StaffDao staffDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createStaff(@RequestBody StaffDto staffDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(staffDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		Staff staff = new Staff();
		staff.setNome(staffDto.getNome());
		staff.setMorada(staffDto.getMorada());
		staff.setTipo_staff(staffDto.getTipoStaff());
		staff.setNif(staffDto.getNif());
		staff.setNacionalidade(staffDto.getNacionalidade());
		staff.setData_nasc(staffDto.getDataNasc());
		staff.setSexo(staffDto.getSexo());
		staff.setCargo(staffDto.getCargo());
		staff.setFoto(staffDto.getFoto());
		
		try {
            staffDao.save(staff);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create staff member, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Staff member successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editStaff(@RequestBody StaffDto staffDto, @RequestParam String idst) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = staffDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(staffDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			staffDao.edit(idst, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit staff. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing staff", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited staff with ID " + idst, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getStaff(@RequestBody StaffDto staffDto) {
		Staff staff = staffDao.findById(staffDto.getId());

		if(staff == null) {
			return new ResponseEntity<>("Could not find staff member with ID <" + staffDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found staff member with NIF: " + staff.getNif(), HttpStatus.OK);
	}
}
