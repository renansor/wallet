package br.com.renansoriano.wallet.core.order;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Order {

	private final UUID id;
	private final UUID person;
	private final ZonedDateTime buyDate;
	private final String type;
	private final String financialInstitute;
	private final String stock;
	private final Integer quantity;
	private final BigDecimal price;
	private final BigDecimal brokeragePrice;

	@Builder
	private Order(
			@NonNull UUID id, 
			@NonNull UUID person,
			@NonNull ZonedDateTime buyDate, 
			@NonNull String type, 
			@NonNull String financialInstitute, 
			@NonNull String stock, 
			@NonNull Integer quantity,
			@NonNull BigDecimal price, 
			@NonNull BigDecimal brokeragePrice) {
		this.id = id;
		this.person = person;
		this.buyDate = buyDate.truncatedTo(ChronoUnit.MILLIS);
		this.type = type;
		this.financialInstitute = financialInstitute;
		this.stock = stock;
		this.quantity = quantity;
		this.price = price.setScale(2);
		this.brokeragePrice = brokeragePrice.setScale(2);
	}
}
