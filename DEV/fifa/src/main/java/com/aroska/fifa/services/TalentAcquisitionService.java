package com.aroska.fifa.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.aroska.fifa.constants.PosicoesAtleta;
import com.aroska.fifa.persistence.daos.administration.core.AtletaDao;
import com.aroska.fifa.persistence.model.administration.core.Atleta;
import com.aroska.fifa.util.AtletaScoreComparator;

public class TalentAcquisitionService {

	//TODO Might be used for other types, so maybe teams too (?)
	AtletaDao atletaDao;
	
	//TODO Add filter list, to be able to filter out for example "golos total" from the final calculation score
	public List<Atleta> fetchResults(String posicao) {
		
		List<Atleta> atletasList = atletaDao.getAllAthletes();
		
		if(!posicao.equals(PosicoesAtleta.NO_FILTER) && posicao != null) {
			filterPosicao(atletasList, posicao);
		}
		
		calculateScoreAtleta(atletasList);
		
		return atletasList;
		
	}
	
	private List<Atleta> filterPosicao(List<Atleta> atletasList, String posicao) {
		
		List<Atleta> processedList = new ArrayList<Atleta>();
		
		for(Atleta atleta : atletasList) {
			if(atleta.getPosicao().equals(posicao)) {
				processedList.add(atleta);
			}
		}
		
		return processedList;
	}
	
	private List<Atleta> calculateScoreAtleta(List<Atleta> atletasList) {
		
		for(Atleta atleta : atletasList) {
			
			int posseBolaScore = calculatePosseBola(atleta.getPosse_bola());
			int golosTotalScore = calculateGolosTotal(atleta.getGolos_total(), atletasList);
			int golosMediaScore = calculateGolosMedia(atleta.getGolos_media(), atletasList);
			
			
			int atletaScore = (posseBolaScore+golosTotalScore+golosMediaScore)/3;
			
			atleta.setPontuacao(atletaScore);
		}
		
		Collections.sort(atletasList, new AtletaScoreComparator());
		
		System.out.println("Sorted list: " + atletasList);
		
		return atletasList;
	}
	

	private int calculatePosseBola(int posseBola) {
		return posseBola/10;
	}
	
	private int calculateGolosTotal(int golosTotal, List<Atleta> atletasList) {
		
		int highestGoals = 0;
		
		for(Atleta a : atletasList) {
			if(a.getGolos_total()>highestGoals) {
				highestGoals = a.getGolos_total();
			}
		}
		
		int calculatedScore = (golosTotal*10)/highestGoals;
		
		return calculatedScore;
	}

	private int calculateGolosMedia(int golosMedia, List<Atleta> atletasList) {
		
		int highestAverageGoals = 0;
		
		for(Atleta a : atletasList) {
			if(a.getGolos_media()> highestAverageGoals) {
				highestAverageGoals = a.getGolos_media();
			}
		}
		
		int calculatedScore = (golosMedia*10)/highestAverageGoals;
		
		return calculatedScore;
	}
}