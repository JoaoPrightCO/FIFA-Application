package com.aroska.fifa.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aroska.fifa.constants.PosicoesAtleta;
import com.aroska.fifa.persistence.model.administration.core.Atleta;
import com.aroska.fifa.services.TalentAcquisitionService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fifa/talentAcquisition")
public class TalentAcquisitionController {
	
	@PostMapping("/get")
	public ResponseEntity<String> getAthleteScores(HttpServletRequest request) {
		
		TalentAcquisitionService taService = new TalentAcquisitionService();
		
		List<Atleta> athleteList = taService.fetchResults(PosicoesAtleta.NO_FILTER);
		
		String athleteString = "";
		
		for(Atleta a : athleteList) {
			athleteString.concat("Nome: " + a.getNome() + " | Pontuacao: " + a.getPontuacao() + "\n");
		}
		
		return new ResponseEntity<>("Athlete scores: \n" + athleteString, HttpStatus.OK);
	}
	
}
