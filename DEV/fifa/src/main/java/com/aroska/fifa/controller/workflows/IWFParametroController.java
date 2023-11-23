package com.aroska.fifa.controller.workflows;

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

import com.aroska.fifa.persistence.daos.workflows.WFParametroDao;
import com.aroska.fifa.persistence.model.workflows.WFParametro;
import com.aroska.fifa.requests.dtos.workflows.WFParametroDto;
import com.aroska.fifa.util.StringUtil;


@RestController
@RequestMapping("/fifa/wfparameters")
public class IWFParametroController {

	@Autowired
	WFParametroDao wfParametroDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createWFParameter(@RequestBody WFParametroDto wfParametroDto){
        
		if(!StringUtil.validate(wfParametroDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		WFParametro wfParametro = new WFParametro();
		wfParametro.setId_accao(wfParametroDto.getId_accao());
		wfParametro.setTipo_entidade(wfParametroDto.getTipoEntidade());
		wfParametro.setCampo(wfParametroDto.getCampo());
		wfParametro.setValor(wfParametroDto.getValor());
		
		try {
            wfParametroDao.save(wfParametro);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create workflow parameter, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Workflow Parameter successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editWFParameter(@RequestBody WFParametroDto wfParametroDto, @RequestParam String idwfp) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = wfParametroDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(wfParametroDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			wfParametroDao.edit(idwfp, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit workflow(parametro). Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing workflow(parametro)", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited workflow(parametro) with ID " + idwfp, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getWFParameter(@RequestBody WFParametroDto wfParametroDto) {
		WFParametro wfParametro = wfParametroDao.findById(wfParametroDto.getId());

		if(wfParametro == null) {
			return new ResponseEntity<>("Could not find workflow parameter with ID <" + wfParametroDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found workflow parameter with ID: " + wfParametro.getId(), HttpStatus.OK);
	}
}
