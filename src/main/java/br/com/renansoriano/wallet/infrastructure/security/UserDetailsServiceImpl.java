package br.com.renansoriano.wallet.infrastructure.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.renansoriano.wallet.core.user.User;
import br.com.renansoriano.wallet.infrastructure.user.JpaUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	JpaUserRepository jpaUserRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = jpaUserRepository.loadByUserName(username);

		return UserDetailsImpl.build(user);
	}

}