package br.com.renansoriano.wallet.core.user;

import java.util.List;
import java.util.UUID;

public interface UserRepository {

	User findByUserId(UUID userId);
	
	List<User> findAll();	

	void save(User user);

	void delete(User user);
	
	void update(User user);
	
	User loadByUserName(String username);
	
	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	Boolean existsByMobile(String mobile);

	Boolean existsByDocument(String document);
	
}
