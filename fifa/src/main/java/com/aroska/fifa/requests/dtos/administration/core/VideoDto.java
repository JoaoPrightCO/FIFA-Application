package com.aroska.fifa.requests.dtos.administration.core;

import lombok.Data;

@Data
public class VideoDto {

	private Integer id;
	private Integer idJogo;
	private Integer idCamara;
	private String video;
	private Integer duracao;

	public String[] getValues() {
    	String[] values = new String[1];
    	
    	values[0] = getVideo();
    	
    	return values;
    }
}
