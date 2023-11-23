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
import com.aroska.fifa.persistence.daos.elearning.FormacaoExtraPlanoDao;
import com.aroska.fifa.persistence.model.elearning.FormacaoExtraPlano;
import com.aroska.fifa.requests.dtos.elearning.FormacaoExtraPlanoDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fifa/formacoes-extra-plano")
public class IFormacaoExtraPlanoController {

	@Autowired
	FormacaoExtraPlanoDao formacaoExtraPlanoDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createFormacaoExtraPlano(@RequestBody FormacaoExtraPlanoDto formacaoExtraPlanoDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(formacaoExtraPlanoDto.getValues())) {
			return new ResponseEntity<>("Some parameters are not correct", HttpStatus.BAD_REQUEST);
		}
		
		FormacaoExtraPlano formacaoExtraPlano = new FormacaoExtraPlano();
		formacaoExtraPlano.setDescricao(formacaoExtraPlanoDto.getDescricao());
		formacaoExtraPlano.setUnidade_organica(formacaoExtraPlanoDto.getUnidade_organica());
		formacaoExtraPlano.setRequisitante(formacaoExtraPlanoDto.getRequisitante());
		formacaoExtraPlano.setArea(formacaoExtraPlanoDto.getArea());
		formacaoExtraPlano.setJustificacao(formacaoExtraPlanoDto.getJustificacao());
		formacaoExtraPlano.setObjectivos_esp(formacaoExtraPlanoDto.getObjectivos_esp());
		formacaoExtraPlano.setCarga_horaria(formacaoExtraPlanoDto.getCarga_horaria());
		formacaoExtraPlano.setTrab_prop(formacaoExtraPlanoDto.getTrab_prop());
		formacaoExtraPlano.setEntidade(formacaoExtraPlanoDto.getEntidade());
		formacaoExtraPlano.setTipo(formacaoExtraPlanoDto.getTipo());
		formacaoExtraPlano.setForma_org(formacaoExtraPlanoDto.getForma_org());
		formacaoExtraPlano.setData_prevista(formacaoExtraPlanoDto.getData_prevista());
		formacaoExtraPlano.setHorario_realizacao(formacaoExtraPlanoDto.getHorario_realizacao());
		formacaoExtraPlano.setLocal_realizacao(formacaoExtraPlanoDto.getLocal_realizacao());
		formacaoExtraPlano.setCusto(formacaoExtraPlanoDto.getCusto());
		formacaoExtraPlano.setConteudo_prog(formacaoExtraPlanoDto.getConteudo_prog());
		formacaoExtraPlano.setEstado(formacaoExtraPlanoDto.getEstado());
		formacaoExtraPlano.setData_pub(formacaoExtraPlanoDto.getData_pub());
		formacaoExtraPlano.setAutor(formacaoExtraPlanoDto.getAutor());
		
		try {
            formacaoExtraPlanoDao.save(formacaoExtraPlano);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create formacao(extraplano), possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Formacao(extraplano) successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editFormacaoExtraPlano(@RequestBody FormacaoExtraPlanoDto formacaoExtraPlanoDto, @RequestParam String idfex) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = formacaoExtraPlanoDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(formacaoExtraPlanoDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			formacaoExtraPlanoDao.edit(idfex, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit formacao(extraplano). Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing formacao(extraplano)", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited formacao(extraplano) with ID " + idfex, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getFormacaoExtraPlano(@RequestParam int id) {
		FormacaoExtraPlano formacaoExtraPlano = formacaoExtraPlanoDao.findById(id);

		if(formacaoExtraPlano == null) {
			return new ResponseEntity<>("Could not find formacao(extraplano) with ID <" + id + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found formacao(extraplano) with description '" + formacaoExtraPlano.getDescricao() + "'", HttpStatus.OK);
	}
}
