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
import com.aroska.fifa.persistence.daos.administration.core.SeguroDao;
import com.aroska.fifa.persistence.model.administration.core.Seguro;
import com.aroska.fifa.requests.dtos.administration.core.SeguroDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/insurance")
public class SeguroController {

	@Autowired
	SeguroDao seguroDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createInsurance(@RequestBody SeguroDto seguroDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(seguroDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		Seguro seguro = new Seguro();
		seguro.setEntidade(seguroDto.getEntidade());
		seguro.setTipo(seguroDto.getTipo());
		seguro.setData_inicio_contr(seguroDto.getDataInicioContr());
		seguro.setPagamento_mensal(seguroDto.getPagamentoMensal());
		seguro.setDescricao(seguroDto.getDescricao());
		seguro.setData_criacao(seguroDto.getDataCriacao());
		seguro.setData_modificacao(seguroDto.getDataModificacao());
		
		try {
            seguroDao.save(seguro);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create insurance, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Insurance successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editInsurance(@RequestBody SeguroDto seguroDto, @RequestParam String ids) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = seguroDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(seguroDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			seguroDao.edit(ids, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit seguro. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing seguro", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited seguro with ID " + ids, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getInsurance(@RequestBody SeguroDto seguroDto) {
		Seguro seguro = seguroDao.findById(seguroDto.getId());

		if(seguro == null) {
			return new ResponseEntity<>("Could not find insurance with ID <" + seguroDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found entity " + seguro.getEntidade() + " with type of insurance: " + seguro.getTipo(), HttpStatus.OK);
	}
}
