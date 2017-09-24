package com.basarbk.editableprofile.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.basarbk.editableprofile.domain.User;
import com.basarbk.editableprofile.domain.dao.UserDao;

@Service
public class UserAuthService implements UserDetailsService{

	@Autowired
	UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		return user;
	}

}
