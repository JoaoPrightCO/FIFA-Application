package com.aroska.fifa.controller.administration.auth;

import com.aroska.fifa.constants.TiposUtilizador;
import com.aroska.fifa.persistence.daos.administration.auth.UtilizadorDao;
import com.aroska.fifa.persistence.model.administration.auth.Utilizador;
import com.aroska.fifa.requests.dtos.administration.auth.LoginDto;
import com.aroska.fifa.requests.dtos.administration.auth.UtilizadorDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fifa/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UtilizadorDao utilizadorDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/signin")
	public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto, HttpServletRequest request) {

		HttpSession session = request.getSession(true);
		
		if (AccountUtil.validateLogin(request, TiposUtilizador.NO_VERIFICATION.getId())) {
			return new ResponseEntity<>("Already logged in as: " + session.getAttribute("currentUser"), HttpStatus.BAD_REQUEST);
		}
		
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Unable to log in with these credentials", HttpStatus.BAD_REQUEST);
		}

		Utilizador user = utilizadorDao.findByUsernameOrEmail(loginDto.getUsernameOrEmail());
		
		System.out.println("NOME DO USER EM AUTH CONTROLLER: " + user.getNome());
		session.setAttribute("user", user);
		session.setAttribute("currentUser", user.getUsername());
		session.setAttribute("nif", user.getNif());
		session.setAttribute("userType", user.getTipo_utilizador());
		session.setAttribute("loggedIn", true);

		return new ResponseEntity<>("User signed-in successfully! Signed in as: " + loginDto.getUsernameOrEmail(),
				HttpStatus.OK);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody UtilizadorDto userDto) {

		if (!StringUtil.validate(userDto.getValues())) {
			return new ResponseEntity<>("Invalid string values", HttpStatus.BAD_REQUEST);
		}

		if (utilizadorDao.findByUsername(userDto.getUsername()) != null) {
			return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
		}

		if (utilizadorDao.findByEmail(userDto.getEmail()) != null) {
			return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
		}
		
		Utilizador user = new Utilizador();
		user.setNome(userDto.getNome());
		user.setUsername(userDto.getUsername());
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setMorada(userDto.getMorada());
		user.setTipo_utilizador(TiposUtilizador.ADMIN.getId());
		user.setNif(userDto.getNif());
		user.setNacionalidade(userDto.getNacionalidade());
		user.setData_nasc(new Date(userDto.getDataNasc().getTime()));
		user.setSexo(userDto.getSexo());
		user.setFoto(userDto.getFoto());

		try {
			utilizadorDao.save(user);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create user, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 

		return new ResponseEntity<>("User registered successfully: " + user.getUsername(), HttpStatus.OK);

	}
	
	@PostMapping("/edit")
	public ResponseEntity<String> editUser(@RequestBody UtilizadorDto userDto, HttpServletRequest request) {
				
		Utilizador user = (Utilizador) request.getSession().getAttribute("user");
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = userDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(utilizadorDao.getColumnNames()[i+1]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			utilizadorDao.edit(String.valueOf(user.getId()), columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit user. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing user", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited user: " + user.getNome(), HttpStatus.OK);
		
	}
	
	@PostMapping("/delete")
	public ResponseEntity<String> deleteUser(HttpServletRequest request) {

		if (!AccountUtil.validateLogin(request, TiposUtilizador.NO_VERIFICATION.getId())) {
			return new ResponseEntity<>("No user currently logged in", HttpStatus.BAD_REQUEST);
		}

		Utilizador user = (Utilizador) request.getSession().getAttribute("user");
		
		utilizadorDao.delete(user);

		request.getSession().invalidate();

		return new ResponseEntity<>("Deleted user: " + user.getNome(), HttpStatus.OK);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logOut(HttpServletRequest request) {

		if (!AccountUtil.validateLogin(request, TiposUtilizador.NO_VERIFICATION.getId())) {
			return new ResponseEntity<>("No user currently logged in", HttpStatus.BAD_REQUEST);
		}

		request.getSession().invalidate();

		return new ResponseEntity<>("User logged out successfully", HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<String> getAllUsers() {
		List<Utilizador> userList = (List<Utilizador>) utilizadorDao.getAllUsers();
		
		String userString = "";
		
		for(int i = 0; i<userList.size(); i++) {
			Utilizador u = userList.get(i);
			userString+=u.getUsername() + "\n";
		}
		
		if(userList == null || userList.isEmpty()) {
			return new ResponseEntity<>("Could not fetch user list", HttpStatus.BAD_REQUEST);
		}
		//TODO why not enhanced for, as below
		
//		for(Utilizador u : userList) {
//			userString.concat(u.getUsername() + "\n");
//		}
		
		return new ResponseEntity<>("User List: \n" + userString, HttpStatus.OK);
	}
	
}