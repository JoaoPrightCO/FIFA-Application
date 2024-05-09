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

import com.aroska.fifa.constants.TiposUtilizador;
import com.aroska.fifa.persistence.daos.workflows.WFAccaoDao;
import com.aroska.fifa.persistence.model.workflows.WFAccao;
import com.aroska.fifa.requests.dtos.workflows.WFAccaoDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/wfactions")
public class WFAccaoController {

	@Autowired
	WFAccaoDao wfAccaoDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createWFAction(@RequestBody WFAccaoDto wfAccaoDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.NORMAL_USER.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(wfAccaoDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		WFAccao wfAccao = new WFAccao();
		wfAccao.setId_wf(wfAccaoDto.getId_wf());
		wfAccao.setAccao(wfAccaoDto.getAccao());
		
		try {
            wfAccaoDao.save(wfAccao);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create workflow action, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Workflow Action successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editWFAction(@RequestBody WFAccaoDto wfAccaoDto, @RequestParam String idwfa) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = wfAccaoDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(wfAccaoDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			wfAccaoDao.edit(idwfa, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit workflow(accao). Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing workflow(accao)", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited workflow(accao) with ID " + idwfa, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getWFAction(@RequestBody WFAccaoDto wfAccaoDto) {
		WFAccao wfAccao = wfAccaoDao.findById(wfAccaoDto.getId());

		if(wfAccao == null) {
			return new ResponseEntity<>("Could not find workflow action with ID <" + wfAccaoDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found workflow action with ID: " + wfAccao.getId_wf(), HttpStatus.OK);
	}
}
