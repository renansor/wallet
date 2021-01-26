package br.com.renansoriano.wallet.core.order;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderRepository {

	List<Order> findByPerson(UUID person);
	
	List<Order> findByPersonAndBuyDate(
			UUID person,
			ZonedDateTime buyDate
			);
	
}
