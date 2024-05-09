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
import com.aroska.fifa.persistence.daos.administration.core.VideoDao;
import com.aroska.fifa.persistence.model.administration.core.Video;
import com.aroska.fifa.requests.dtos.administration.core.VideoDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/fifa/videos")
public class VideoController {

	@Autowired
	VideoDao videoDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createVideo(@RequestBody VideoDto videoDto, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(videoDto.getValues())) {
			return new ResponseEntity<>("Some parameters were not filled", HttpStatus.BAD_REQUEST);
		}
		
		Video video = new Video();
		video.setId_jogo(videoDto.getIdJogo());
		video.setId_camara(videoDto.getIdCamara());
		video.setVideo(videoDto.getVideo());
		video.setDuracao(videoDto.getDuracao());
		
		try {
            videoDao.save(video);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create video, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Video successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editVideo(@RequestBody VideoDto videoDto, @RequestParam String idv) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = videoDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(videoDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			videoDao.edit(idv, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit video. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing video", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited video with ID " + idv, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getVideo(@RequestBody VideoDto videoDto) {
		Video video = videoDao.findById(videoDto.getId());

		if(video == null) {
			return new ResponseEntity<>("Could not find video with ID <" + videoDto.getId() + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found video with match ID: " + video.getId_jogo() + " and camera ID: " + video.getId_camara(), HttpStatus.OK);
	}
}
