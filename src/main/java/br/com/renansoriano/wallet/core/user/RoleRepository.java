package br.com.renansoriano.wallet.core.user;

import java.util.Optional;

import br.com.renansoriano.wallet.core.user.Role.ERole;

public interface RoleRepository {

	Optional<Role> findByName(ERole name);
	
}