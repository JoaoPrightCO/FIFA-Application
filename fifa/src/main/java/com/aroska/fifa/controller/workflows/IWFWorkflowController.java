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
import com.aroska.fifa.persistence.daos.workflows.WFWorkflowDao;
import com.aroska.fifa.persistence.model.workflows.WFWorkflow;
import com.aroska.fifa.requests.dtos.workflows.WFWorkflowDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/workflow")
public class IWFWorkflowController {

	@Autowired
	WFWorkflowDao wfWorkflowDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createWorkflow(@RequestBody WFWorkflowDto wfWorkflowDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.NORMAL_USER.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(wfWorkflowDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		WFWorkflow wfWorkflow = new WFWorkflow();
		wfWorkflow.setId_user(wfWorkflowDto.getId_user());
		wfWorkflow.setNome(wfWorkflowDto.getNome());
		
		try {
            wfWorkflowDao.save(wfWorkflow);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create workflow, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Workflow successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editWorkflow(@RequestBody WFWorkflowDto wfWorkflowDto, @RequestParam String idwf) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = wfWorkflowDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(wfWorkflowDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			wfWorkflowDao.edit(idwf, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit workflow. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing workflow", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited workflow with ID " + idwf, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getWorkflow(@RequestBody WFWorkflowDto wfWorkflowDto) {
		WFWorkflow wfWorkflow = wfWorkflowDao.findById(wfWorkflowDto.getId());

		if(wfWorkflow == null) {
			return new ResponseEntity<>("Could not find workflow with ID <" + wfWorkflowDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found workflow with ID: " + wfWorkflow.getId() + " belonging to user ID: " + wfWorkflow.getId_user(), HttpStatus.OK);
	}
}
