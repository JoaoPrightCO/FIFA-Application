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
import com.aroska.fifa.persistence.daos.administration.types.TipoEntidadeDao;
import com.aroska.fifa.persistence.model.administration.types.TipoEntidade;
import com.aroska.fifa.requests.dtos.administration.types.TipoEntidadeDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/entityType")
public class ITipoEntidadeController {

	@Autowired
	TipoEntidadeDao tipoEntidadeDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createEntityType(@RequestBody TipoEntidadeDto tipoEntidadeDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(tipoEntidadeDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		TipoEntidade tipoEntidade = new TipoEntidade();
		tipoEntidade.setNome_curto(tipoEntidadeDto.getNome_curto());
		tipoEntidade.setNome_ext(tipoEntidadeDto.getNome_ext());
		
		try {
            tipoEntidadeDao.save(tipoEntidade);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create entity type, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Entity type successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editEntityType(@RequestBody TipoEntidadeDto tipoEntidadeDto, @RequestParam String idtpent) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = tipoEntidadeDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(tipoEntidadeDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			tipoEntidadeDao.edit(idtpent, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit tipo(entidade). Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing tipo(entidade)", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited tipo(entidade) with ID " + idtpent, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getEntityType(@RequestBody TipoEntidadeDto tipoEntidadeDto) {
		TipoEntidade tipoEntidade = tipoEntidadeDao.findById(tipoEntidadeDto.getId());

		if(tipoEntidade == null) {
			return new ResponseEntity<>("Could not find entity type with ID <" + tipoEntidadeDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found entity type: " + tipoEntidade.getNome_curto(), HttpStatus.OK);
	}
}
