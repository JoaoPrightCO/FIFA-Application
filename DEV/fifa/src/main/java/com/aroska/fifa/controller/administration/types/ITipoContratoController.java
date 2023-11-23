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
import com.aroska.fifa.persistence.daos.administration.types.TipoContratoDao;
import com.aroska.fifa.persistence.model.administration.types.TipoContrato;
import com.aroska.fifa.requests.dtos.administration.types.TipoContratoDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/contractType")
public class ITipoContratoController {

	@Autowired
	TipoContratoDao tipoContratoDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createContractType(@RequestBody TipoContratoDto tipoContratoDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(tipoContratoDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		TipoContrato tipoContrato = new TipoContrato();
		tipoContrato.setNome_curto(tipoContratoDto.getNome_curto());
		tipoContrato.setNome_ext(tipoContratoDto.getNome_ext());
		
		try {
            tipoContratoDao.save(tipoContrato);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create contract type, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Contract type successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editContractType(@RequestBody TipoContratoDto tipoContratoDto, @RequestParam String idtpct) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = tipoContratoDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(tipoContratoDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			tipoContratoDao.edit(idtpct, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit tipo(contrato). Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing tipo(contrato)", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited tipo(contrato) with ID " + idtpct, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getContractType(@RequestBody TipoContratoDto tipoContratoDto) {
		TipoContrato tipoContrato = tipoContratoDao.findById(tipoContratoDto.getId());

		if(tipoContrato == null) {
			return new ResponseEntity<>("Could not find contract type with ID <" + tipoContratoDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found contract type: " + tipoContrato.getNome_curto(), HttpStatus.OK);
	}
}
