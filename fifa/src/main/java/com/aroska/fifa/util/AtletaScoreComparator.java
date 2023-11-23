package com.aroska.fifa.util;

import java.util.Comparator;

import com.aroska.fifa.persistence.model.administration.core.Atleta;

public class AtletaScoreComparator implements Comparator<Atleta> {

	@Override
	public int compare(Atleta a1, Atleta a2) {
		return a1.getPontuacao() - a2.getPontuacao();
	}

}
