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

	private static final String QUERY_FIND_ALL = "SELECT p FROM UserEntity p";
	private static final String QUERY_FIND_BY_USER_ID = "SELECT p FROM UserEntity p WHERE p.id = :id";
	private static final String QUERY_FIND_BY_USER_NAME = "SELECT p FROM UserEntity p WHERE p.userName = :name";
	private static final String QUERY_USER_NAME_EXISTS = "SELECT count(p) FROM UserEntity p WHERE p.userName = :name";
	private static final String QUERY_EMAIL_EXISTS = "SELECT count(p) FROM UserEntity p WHERE p.email = :email";
	private static final String QUERY_MOBILE_EXISTS = "SELECT count(p) FROM UserEntity p WHERE p.mobile = :mobile";
	private static final String QUERY_DOCUMENT_EXISTS = "SELECT count(p) FROM UserEntity p WHERE p.document = :document";
	
	private EntityManager entityManager;

	public JpaUserRepository(
			EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Transactional
	@Override
	public List<User> findAll() {
		
		logger.info("Finding by all users");

		List<UserEntity> entities = entityManager
				.createQuery(QUERY_FIND_ALL, UserEntity.class)
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
	public User findByUserId(UUID id) {
		
		logger.info("Finding by id {}", id);

		UserEntity entity = entityManager
				.createQuery(QUERY_FIND_BY_USER_ID, UserEntity.class)
				.setParameter("id", id)
				.getSingleResult();
		
		logger.info("Found entity {}", entity);

		User user = entity.toUser();
		
		logger.info("Converted to User {}", user);

		return user;
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
	public void delete(User user) {
		logger.info("Deleting User {}", user);
		
		try {
			entityManager.remove(
					entityManager.contains(UserEntity.of(user)) 
						? UserEntity.of(user) 
						: entityManager.merge(UserEntity.of(user))
			);	
		} catch (Exception exception) {
			throw new UserSaveException(
					exception, 
					"was not possible to delete user %s",
					user);
		}
		
		logger.info("Deleted");
	}

	@Transactional
	@Override
	public void update(User user) {
		logger.info("Updating User {}", user);

		try {
			entityManager.merge(UserEntity.of(user));	
		} catch (Exception exception) {
			throw new UserSaveException(
					exception, 
					"was not possible to update user %s",
					user);
		}
		
	}
	
	public User loadByUserName(String username) {	
		
		logger.info("loading by username {}", username);

		UserEntity entity = entityManager
				.createQuery(QUERY_FIND_BY_USER_NAME, UserEntity.class)
				.setParameter("name", username)
				.getSingleResult();
		
		logger.info("Found entity {}", entity);

		User user = entity.toUser();
		
		logger.info("Converted to User {}", user);

		return user;
	}

	@Override
	public Boolean existsByUsername(String username) {
		
		logger.info("checking if username exists {}", username);

		Long count = (Long) entityManager
				.createQuery(QUERY_USER_NAME_EXISTS)
				.setParameter("name", username)
				.getSingleResult();
		
		Boolean exists;
		
		if (count > 0) {
			exists = true;
		} else {
			exists = false;
		}
		
		logger.info("Found username {}", exists);

		return exists;
	}

	@Override
	public Boolean existsByEmail(String email) {
		logger.info("checking if email exists {}", email);

		Long count = (Long) entityManager
				.createQuery(QUERY_EMAIL_EXISTS)
				.setParameter("email", email)
				.getSingleResult();
		
		Boolean exists;
		
		if (count > 0) {
			exists = true;
		} else {
			exists = false;
		}
		
		logger.info("Found email {}", exists);

		return exists;
	}
	
	@Override
	public Boolean existsByMobile(String mobile) {
		logger.info("checking if mobile exists {}", mobile);

		Long count = (Long) entityManager
				.createQuery(QUERY_MOBILE_EXISTS)
				.setParameter("mobile", mobile)
				.getSingleResult();
		
		Boolean exists;
		
		if (count > 0) {
			exists = true;
		} else {
			exists = false;
		}
		
		logger.info("Found mobile {}", exists);

		return exists;
	}
	
	@Override
	public Boolean existsByDocument(String document) {
		logger.info("checking if document exists {}", document);

		Long count = (Long) entityManager
				.createQuery(QUERY_DOCUMENT_EXISTS)
				.setParameter("document", document)
				.getSingleResult();
		
		Boolean exists;
		
		if (count > 0) {
			exists = true;
		} else {
			exists = false;
		}
		
		logger.info("Found document {}", exists);

		return exists;
	}
}
