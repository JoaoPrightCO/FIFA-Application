package com.aroska.fifa.services;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.aroska.fifa.persistence.model.administration.auth.Utilizador;

public class UtilizadorDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	private Utilizador utilizador;
	
	public UtilizadorDetails (Utilizador utilizador) {
		this.utilizador = utilizador;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		return utilizador.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return utilizador.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
