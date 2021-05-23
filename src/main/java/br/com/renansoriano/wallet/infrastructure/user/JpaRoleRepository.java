package br.com.renansoriano.wallet.infrastructure.user;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.renansoriano.wallet.core.user.Role;
import br.com.renansoriano.wallet.core.user.Role.ERole;
import br.com.renansoriano.wallet.core.user.RoleRepository;

@Component
public class JpaRoleRepository implements RoleRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(JpaRoleRepository.class);

	private static final String QUERY_FIND_BY_NAME = "SELECT p FROM RoleEntity p WHERE p.name = :name";

	private EntityManager entityManager;

	public JpaRoleRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	@Override
	public Optional<Role> findByName(ERole name) {

		logger.info("Finding by name {}", name);

		RoleEntity entity = entityManager
				.createQuery(QUERY_FIND_BY_NAME, RoleEntity.class)
				.setParameter("name", name)
				.getSingleResult();
		
		logger.info("Found entity {}", entity);

		Role role = entity.toRole();
		
		logger.info("Converted to role {}", role);

		return Optional.ofNullable(role);		
	}

}
