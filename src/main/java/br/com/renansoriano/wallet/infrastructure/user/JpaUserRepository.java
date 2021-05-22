package br.com.renansoriano.wallet.infrastructure.user;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.renansoriano.wallet.core.user.User;
import br.com.renansoriano.wallet.core.user.UserRepository;

@Component
public class JpaUserRepository implements UserRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(JpaUserRepository.class);

	private static final String QUERY_FIND_BY_USER_ID = "SELECT p FROM UserEntity p WHERE p.id = :id";
	private static final String QUERY_DELETE_BY_USER_ID = "DELETE FROM UserEntity WHERE id = :id";
	private static final String QUERY_UPDATE_BY_USER_ID = "UPDATE UserEntity  " ;

	private EntityManager entityManager;

	public JpaUserRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	@Override
	public List<User> findByUserId(UUID id) {
		
		logger.info("Finding by id {}", id);

		List<UserEntity> entities = entityManager
				.createQuery(QUERY_FIND_BY_USER_ID, UserEntity.class)
				.setParameter("userId", id)
				.getResultList();
		
		logger.info("Found entities {}", entities);

		List<User> users = entities.stream()
				.map(UserEntity::toUser)
				.collect(Collectors.toList());
		
		logger.info("Converted to User {}", users);

		return users;
	}

	@Transactional
	@Override
	public void save(User user) {
		
		logger.info("Persisting User {}", user);
		
		try {
			entityManager.persist(UserEntity.of(user));
		} catch (Exception exception) {
			throw new UserSaveException(
					exception, 
					"was not possible to save user %s",
					user);
		}
		
		logger.info("Persisted");
	}
	
	@Transactional
	@Override
	public void delete(UUID userId) {
		logger.info("Deleting User id {}", userId);
		
		try {
			entityManager.createQuery(QUERY_DELETE_BY_USER_ID, UserEntity.class)
			.setParameter("id", userId);
		} catch (Exception exception) {
			throw new UserSaveException(
					exception, 
					"was not possible to delete user %s",
					userId);
		}
		
		logger.info("Deleted");
	}

	@Transactional
	@Override
	public void update(User user) {
		logger.info("Updating User {}", user);

		List<User> userDataBase = findByUserId(user.getId());
		 
	
		//usario.set
		
	}
}
