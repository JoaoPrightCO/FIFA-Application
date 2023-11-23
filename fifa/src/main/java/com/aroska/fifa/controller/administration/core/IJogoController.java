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
import com.aroska.fifa.persistence.daos.administration.core.JogoDao;
import com.aroska.fifa.persistence.model.administration.core.Jogo;
import com.aroska.fifa.requests.dtos.administration.core.JogoDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/matches")
public class IJogoController {

	@Autowired
	JogoDao jogoDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createMatch(@RequestBody JogoDto jogoDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(jogoDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		Jogo jogo = new Jogo();
		jogo.setId_equipa1(jogoDto.getIdEquipa1());
		jogo.setId_equipa2(jogoDto.getIdEquipa2());
		jogo.setId_evento(jogoDto.getIdEvento());
		jogo.setLocalizacao(jogoDto.getLocalizacao());
		jogo.setDur_prolongamento(jogoDto.getDurProlongamento());
		jogo.setEquipa_vencedora(jogoDto.getEquipaVencedora());
		jogo.setGolos_equipa1(jogoDto.getGolosEquipa1());
		jogo.setGolos_equipa2(jogoDto.getGolosEquipa2());
		jogo.setNr_substituicoes(jogoDto.getNrSubstituicoes());
		jogo.setNr_faltas(jogoDto.getNrFaltas());
		jogo.setPosse_equipa1(jogoDto.getPosseEquipa1());
		jogo.setPosse_equipa2(jogoDto.getPosseEquipa2());
		jogo.setEst_metereologico(jogoDto.getEstMetereologico());
		
		try {
            jogoDao.save(jogo);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create match, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Match successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editMatch(@RequestBody JogoDto jogoDto, @RequestParam String idjg) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = jogoDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(jogoDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			jogoDao.edit(idjg, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit jogo. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing jogo", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited jogo with ID " + idjg, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getMatch(@RequestBody JogoDto jogoDto) {
		Jogo jogo = jogoDao.findById(jogoDto.getId());

		if(jogo == null) {
			return new ResponseEntity<>("Could not find match with ID <" + jogoDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found match in " + jogo.getLocalizacao(), HttpStatus.OK);
	}
}
