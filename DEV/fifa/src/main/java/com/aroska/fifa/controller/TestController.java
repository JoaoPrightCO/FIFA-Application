package com.aroska.fifa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aroska.fifa.constants.TiposUtilizador;
import com.aroska.fifa.persistence.daos.administration.types.TipoUtilizadorDao;
import com.aroska.fifa.persistence.model.administration.types.TipoUtilizador;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/fifa/test")
public class TestController {

	//TODO REMOVE CLASS (FOR TEST PURPOSES ONLY)	
	
	@PostMapping("/sessionCreate")
	public ResponseEntity<String> createSession(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.setAttribute("testAttribute", "7");
		
		System.out.println("Session ID: " + session.getId());
		
		return new ResponseEntity<>("Was able to store session", HttpStatus.OK);
	}
	
	@GetMapping("/sessionGet")
	public ResponseEntity<String> getSession(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("testAttribute") == null) {
			return new ResponseEntity<>("Attribute is null", HttpStatus.OK);
		}
		
		
		System.out.println("get attribute: " + session.getAttribute("testAttribute"));
		
		return new ResponseEntity<>("Sucessfuly obtained attribute. Value: " + session.getAttribute("testAttribute"), HttpStatus.OK);
	}
	
	@PostMapping("/sessionInvalidate")
	public ResponseEntity<String> invalidateSession(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		session.invalidate();
		
		return new ResponseEntity<>("Session invalidated", HttpStatus.OK);
	}
	
	

	//test with autowired DAO
	@GetMapping("/getUserType")
	public ResponseEntity<String> getUserType(HttpServletRequest request) {
		
		TipoUtilizadorDao tuDao = new TipoUtilizadorDao();
		
		TipoUtilizador tipo = tuDao.findById(TiposUtilizador.ADMIN.getId());
		
		return new ResponseEntity<>("Got user type", HttpStatus.OK);
	}
	
}
