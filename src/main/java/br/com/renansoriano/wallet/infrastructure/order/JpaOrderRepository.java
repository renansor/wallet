package br.com.renansoriano.wallet.infrastructure.order;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.renansoriano.wallet.core.order.Order;
import br.com.renansoriano.wallet.core.order.OrderRepository;

@Component
public class JpaOrderRepository implements OrderRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(JpaOrderRepository.class);

	private static final String QUERY_FIND_BY_USER_ID = "SELECT p FROM OrderEntity p WHERE p.userId = :userId ORDER BY p.buyDate DESC";
	private static final String QUERY_FIND_BY_USER_ID_AND_BUY_DATE = "SELECT p FROM OrderEntity p WHERE p.userId = :userId AND (p.buyDate >= :buyDate and p.buyDate <= :buyDate) ORDER BY p.buyDate DESC";

	private EntityManager entityManager;

	public JpaOrderRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	@Override
	public List<Order> findByUserId(UUID userId) {
		
		logger.info("Finding OrderEntity by userId {}", userId);

		List<OrderEntity> entities = entityManager
				.createQuery(QUERY_FIND_BY_USER_ID, OrderEntity.class)
				.setParameter("userId", userId)
				.getResultList();
		
		logger.info("Found entities {}", entities);

		List<Order> orders = entities.stream()
				.map(OrderEntity::toOrder)
				.collect(Collectors.toList());
		
		logger.info("Converted to Order {}", orders);

		return orders;
	}

	@Transactional
	@Override
	public List<Order> findByUserIdAndBuyDate(UUID userId, ZonedDateTime buyDate) {
		
		logger.info("Finding OrderEntity by userId {} and buyDate {}", userId, buyDate);

		List<OrderEntity> entities = entityManager.createQuery(QUERY_FIND_BY_USER_ID_AND_BUY_DATE, OrderEntity.class)
				.setParameter("userId", userId)
				.setParameter("buyDate", buyDate)
				.getResultList();
		
		logger.info("Found entities {}", entities);

		List<Order> orders = entities.stream()
				.map(OrderEntity::toOrder)
				.collect(Collectors.toList());
		
		logger.info("Converted to Order {}", orders);

		return orders;
	}

	@Transactional
	@Override
	public void save(Order order) {
		
		logger.info("Persisting Order {}", order);
		
		try {
			entityManager.persist(OrderEntity.of(order));
		} catch (Exception exception) {
			throw new OrderSaveException(
					exception, 
					"was not possible to save order %s",
					order);
		}
		
		logger.info("Persisted");
	}
}
