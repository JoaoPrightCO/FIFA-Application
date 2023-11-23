package com.aroska.fifa.services;

import com.aroska.fifa.persistence.daos.administration.auth.UtilizadorDao;
import com.aroska.fifa.persistence.model.administration.auth.Utilizador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
    private UtilizadorDao utilizadorDao;

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		
		Utilizador utilizador = utilizadorDao.findByUsernameOrEmail(usernameOrEmail);
		
		return new UtilizadorDetails(utilizador);
	}

}