package br.com.renansoriano.wallet.infrastructure.order;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import br.com.renansoriano.wallet.core.order.Order;
import br.com.renansoriano.wallet.infrastructure.configuration.BeansConfigurer;
import br.com.renansoriano.wallet.infrastructure.jpa.EntityManagerProvider;

@EnableAutoConfiguration
@SpringBootTest( classes = {BeansConfigurer.class, EntityManagerProvider.class})
@ComponentScan("br.com.renansoriano.wallet.infrastructure.order")
public class JpaOrderRepositoryTest {
	
	@Autowired
	private EntityManagerProvider entityManagerProvider;

	@Autowired
	private JpaOrderRepository repository;

	private Order order1;
	private UUID personId = UUID.fromString("14100122-520b-4ed0-8d24-8aa857c6c8b0");
	private ZonedDateTime buyDate = ZonedDateTime.now();

	@BeforeEach
	public void setup() {

		order1 = Order.builder()
				.id(UUID.randomUUID())
				.person(UUID.randomUUID())
				.buyDate(ZonedDateTime.now())
				.type("COMPRA")
				.financialInstitute("ITAU")
				.stock("ITUB4")
				.quantity(200)
				.price(new BigDecimal(30))
				.brokeragePrice(new BigDecimal(1))
				.build();

		entityManagerProvider.executeInNewTransaction(em -> {
			em.persist(OrderEntity.of(order1));
		});

	}

	public void clear() {
		entityManagerProvider.executeInNewTransaction(em -> {
			em.createQuery("DELETE FROM OrderEntity").executeUpdate();
		});
	}

	@Test
	public void findByPerson() {
		
		List<Order> result = repository.findByPerson(personId);
		assertThat(result).isEqualTo(List.of(order1));

	}

	@Test
	public void findByPersonAndBuyDate() {
		
		List<Order> result = repository.findByPersonAndBuyDate(personId, buyDate);
		assertThat(result).isEqualTo(List.of(order1));

	}

}
