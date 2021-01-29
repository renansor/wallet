package br.com.renansoriano.wallet.infrastructure.order;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import br.com.renansoriano.wallet.core.order.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class OrderEntity {

	@Id
	@Column(name = "id", nullable = false)
	@Type(type = "uuid-binary")
	private UUID id;
	
	@Column(name = "person", nullable = false)
	@Type(type = "uuid-binary")
	private UUID person;

	@Column(name = "buy_date", nullable = false)
	private ZonedDateTime buyDate;

	@Column(name = "type", nullable = false)
	private String type;

	@Column(name = "financial_institute", nullable = false)
	private String financialInstitute;

	@Column(name = "stock", nullable = false)
	private String stock;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@Column(name = "brokerage_price", nullable = false)
	private BigDecimal brokeragePrice;

	public Order toOrder() {
		return Order.builder()
				.id(id)
				.person(person)
				.buyDate(buyDate)
				.type(type)
				.financialInstitute(financialInstitute)
				.stock(stock)
				.quantity(quantity)
				.price(brokeragePrice)
				.brokeragePrice(brokeragePrice)
				.build();
	}

	public static OrderEntity of(Order order) {

		OrderEntity entity = new OrderEntity();
		entity.setId(order.getId());
		entity.setPerson(order.getPerson());
		entity.setBuyDate(order.getBuyDate());
		entity.setType(order.getType());
		entity.setFinancialInstitute(order.getFinancialInstitute());
		entity.setStock(order.getStock());
		entity.setQuantity(order.getQuantity());
		entity.setPrice(order.getPrice());
		entity.setBrokeragePrice(order.getBrokeragePrice());

		return entity;
	}
}
