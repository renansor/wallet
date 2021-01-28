package br.com.renansoriano.wallet.infrastructure.order;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.renansoriano.wallet.core.order.Order;
import br.com.renansoriano.wallet.core.order.OrderRepository;

@Component
public class JpaOrderRepository implements OrderRepository {

	private static final String QUERY_FIND_BY_PERSON = "SELECT p FROM OrderEntity p WHERE p.person = :person ORDER BY p.buyDate DESC";
	private static final String QUERY_FIND_BY_PERSON_AND_BUY_DATE = "SELECT p FROM OrderEntity p WHERE p.person = :person AND (p.buyDate >= :buyDate and p.buyDate <= :buyDate) ORDER BY p.buyDate DESC";

	private EntityManager entityManager;

	public JpaOrderRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	@Override
	public List<Order> findByPerson(UUID person) {

		List<OrderEntity> entities = entityManager.createQuery(QUERY_FIND_BY_PERSON, OrderEntity.class)
				.setParameter("person", person).getResultList();

		List<Order> orders = entities.stream().map(OrderEntity::toOrder).collect(Collectors.toList());

		return orders;
	}

	@Transactional
	@Override
	public List<Order> findByPersonAndBuyDate(UUID person, ZonedDateTime buyDate) {

		List<OrderEntity> entities = entityManager.createQuery(QUERY_FIND_BY_PERSON_AND_BUY_DATE, OrderEntity.class)
				.setParameter("person", person).setParameter("buyDate", buyDate).getResultList();

		List<Order> orders = entities.stream().map(OrderEntity::toOrder).collect(Collectors.toList());

		return orders;
	}

	@Transactional
	@Override
	public void save(Order order) {
		try {
			entityManager.persist(OrderEntity.of(order));
		} catch (Exception exception) {
			throw new OrderSaveException(
					exception, 
					"was not possible to save saldo %s",
					order);
		}
	}
}
