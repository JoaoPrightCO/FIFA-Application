package com.aroska.fifa.controller.elearning;

import java.util.ArrayList;
import java.util.List;

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
import com.aroska.fifa.persistence.daos.elearning.FormandoDao;
import com.aroska.fifa.persistence.model.administration.auth.Utilizador;
import com.aroska.fifa.persistence.model.elearning.Formando;
import com.aroska.fifa.requests.dtos.elearning.FormandoDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/fifa/formandos")
public class IFormandoController {

	@Autowired
	FormandoDao formandoDao;
	
	//TODO este controlador é um sistema de inscrição/subscrição, é diferente
	@PostMapping("/create")
    public ResponseEntity<String> createFormando(@RequestBody FormandoDto formandoDto, @RequestParam int idf, HttpServletRequest request){

		HttpSession session = request.getSession();
		
		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(formandoDto.getValues())) {
			return new ResponseEntity<>("Some parameters are not correct", HttpStatus.BAD_REQUEST);
		}
		
		Utilizador user = (Utilizador) session.getAttribute("user");
				
		Formando formando = new Formando();
		formando.setId_user(user.getId());
		formando.setId_formacao(idf);
		formando.setData_inicio(formandoDto.getData_inicio());
		formando.setData_fim(formandoDto.getData_fim());
		formando.setEstado(formandoDto.getEstado());
		
		try {
            formandoDao.save(formando);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create formando, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Formando successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editFormando(@RequestBody FormandoDto formandoDto, @RequestParam String idfnd) {
		
		//TODO different type of way of editing (auxiliary table with no ID)
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = formandoDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(formandoDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
//			formandoDao.edit(idfnd, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit formando. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing formando", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited formando with ID " + idfnd, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getFormando(@RequestParam int id) {
		List<Formando> formandoList = (List<Formando>) formandoDao.findByIdUser(id);
		
		String formacoesString = "";
		
		for(int i = 0; i<formandoList.size(); i++) {
			Formando u = formandoList.get(i);
			formacoesString+=u.getId_formacao() + "\n";
		}
		
		if(formandoList == null || formandoList.isEmpty()) {
			return new ResponseEntity<>("Could not find formando with ID <" + id + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Formando with id '" + id + "' has the following formacoes: \n" + formacoesString, HttpStatus.OK);
	}
}
