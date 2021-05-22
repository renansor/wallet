package br.com.renansoriano.wallet.core.user;

import java.util.List;
import java.util.UUID;

public interface UserRepository {

	User findByUserId(UUID userId);

	void save(User user);
	
	void update(User user);

	void delete(UUID userId);

	List<User> findAll();	
	
}
