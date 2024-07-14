package com.Library.Secutiyconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.Library.dao.Library_Repository;
import com.Library.model.Library;


@Component
public class UserDetailsServiceImple implements UserDetailsService{
	
	@Autowired
	public Library_Repository library_Repository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Library library=this.library_Repository.findByEmail(username);
		
	       if(library==null)
	       {
	    	   throw new UsernameNotFoundException("could not found user");
	       }else {
	       
	       //CustomUserDetails customUserDetails=new CustomUserDetails();
	       
			return new CustomUserDetails(library);
	       }
	}

}
