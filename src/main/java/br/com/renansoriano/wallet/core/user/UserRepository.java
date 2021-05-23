package br.com.renansoriano.wallet.core.user;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository {

	User findByUserId(UUID userId);
	
	List<User> findAll();	

	void save(User user);

	void delete(User user);
	
	void update(User user);
	
	UserDetails loadByUserName(String username);
	
}
