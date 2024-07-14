package com.Library.Secutiyconfig;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.Library.model.Library;

@Component
public class CustomUserDetails implements UserDetails{
    
	private Library library;
	
	

	public CustomUserDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomUserDetails(Library library) {
		super();
		this.library = library;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(library.getRole());
		return List.of(simpleGrantedAuthority);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return library.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return library.getEmail();
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
