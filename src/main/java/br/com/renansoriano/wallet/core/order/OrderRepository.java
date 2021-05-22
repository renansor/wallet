package br.com.renansoriano.wallet.core.order;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderRepository {

	List<Order> findByUserId(UUID userId);
	
	List<Order> findByUserIdAndBuyDate(
			UUID userId,
			ZonedDateTime buyDate
			);

	void save(Order order);
	
}
