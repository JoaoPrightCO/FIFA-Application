package com.aroska.fifa.controller.elearning;

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
import com.aroska.fifa.persistence.daos.elearning.FormacaoDao;
import com.aroska.fifa.persistence.model.elearning.Formacao;
import com.aroska.fifa.requests.dtos.elearning.FormacaoDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fifa/formacoes")
public class IFormacaoController {

	@Autowired
	FormacaoDao formacaoDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createFormacao(@RequestBody FormacaoDto formacaoDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(formacaoDto.getValues())) {
			return new ResponseEntity<>("Some parameters are not correct", HttpStatus.BAD_REQUEST);
		}
		
		Formacao formacao = new Formacao();
		formacao.setNome(formacaoDto.getNome());
		formacao.setDescricao(formacaoDto.getDescricao());
		formacao.setEntidade(formacaoDto.getEntidade());
		formacao.setTipo(formacaoDto.getTipo());
		formacao.setForma(formacaoDto.getForma());
		formacao.setCompetencias(formacaoDto.getCompetencias());
		formacao.setArea(formacaoDto.getArea());
		formacao.setTrab_prop(formacaoDto.getTrab_prop());
		formacao.setEstado(formacaoDto.getEstado());
		formacao.setData_pub(formacaoDto.getData_pub());
		formacao.setAutor(formacaoDto.getAutor());
		formacao.setImagem(formacaoDto.getImagem());
		
		try {
            formacaoDao.save(formacao);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create formacao, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Formacao successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editFormacao(@RequestBody FormacaoDto formacaoDto, @RequestParam String idf) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = formacaoDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(formacaoDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			formacaoDao.edit(idf, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit formacao. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing formacao", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited formacao with ID " + idf, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getFormacao(@RequestParam int id) {
		Formacao formacao = formacaoDao.findById(id);

		if(formacao == null) {
			return new ResponseEntity<>("Could not find formacao with ID <" + id + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found formacao named " + formacao.getNome(), HttpStatus.OK);
	}
}
