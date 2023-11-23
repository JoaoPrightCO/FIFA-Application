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
import com.aroska.fifa.persistence.daos.administration.types.TipoAgenteDao;
import com.aroska.fifa.persistence.model.administration.types.TipoAgente;
import com.aroska.fifa.requests.dtos.administration.types.TipoAgenteDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/agentType")
public class ITipoAgenteController {

	@Autowired
	TipoAgenteDao tipoAgenteDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createAgentType(@RequestBody TipoAgenteDto tipoAgenteDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(tipoAgenteDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		TipoAgente tipoAgente = new TipoAgente();
		tipoAgente.setNome_curto(tipoAgenteDto.getNome_curto());
		tipoAgente.setNome_ext(tipoAgenteDto.getNome_ext());
		
		try {
            tipoAgenteDao.save(tipoAgente);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create agent type, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Agent type successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editAgentType(@RequestBody TipoAgenteDto tipoAgenteDto, @RequestParam String idtpag) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = tipoAgenteDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(tipoAgenteDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			tipoAgenteDao.edit(idtpag, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit tipo(agente). Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing tipo(agente)", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited tipo(agente) with ID " + idtpag, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getAgentType(@RequestBody TipoAgenteDto tipoAgenteDto) {
		TipoAgente tipoAgente = tipoAgenteDao.findById(tipoAgenteDto.getId());

		if(tipoAgente == null) {
			return new ResponseEntity<>("Could not find agent type with ID <" + tipoAgenteDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found agent type: " + tipoAgente.getNome_curto(), HttpStatus.OK);
	}
}
