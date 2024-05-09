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
import com.aroska.fifa.persistence.daos.administration.core.EventoDao;
import com.aroska.fifa.persistence.model.administration.core.Evento;
import com.aroska.fifa.requests.dtos.administration.core.EventoDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fifa/event")
public class EventoController {

	@Autowired
	EventoDao eventoDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createEvent(@RequestBody EventoDto eventoDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(eventoDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		Evento evento = new Evento();
		evento.setNome(eventoDto.getNome());
		evento.setEntidade(eventoDto.getEntidade());
		evento.setLocalizacao(eventoDto.getLocalizacao());
		evento.setData(eventoDto.getData());
		evento.setData_criacao(eventoDto.getDataCriacao());
		evento.setData_modificacao(eventoDto.getDataModificacao());
		
		try {
            eventoDao.save(evento);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create event, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Event successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editEvent(@RequestBody EventoDto eventoDto, @RequestParam String idev) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = eventoDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(eventoDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			eventoDao.edit(idev, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit evento. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing evento", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited evento with ID " + idev, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getEvent(@RequestBody EventoDto eventoDto) {
		Evento evento = eventoDao.findById(eventoDto.getId());

		if(evento == null) {
			return new ResponseEntity<>("Could not find event with ID <" + eventoDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found event named " + evento.getNome(), HttpStatus.OK);
	}
}
