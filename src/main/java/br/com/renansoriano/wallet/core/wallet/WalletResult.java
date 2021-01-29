package br.com.renansoriano.wallet.core.wallet;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class WalletResult {
	
	private final UUID person;
	private final String type;
	private final String stock;
	private final Integer sumQuantity;
	private final BigDecimal avgPrice;
	private final BigDecimal avgBrokeragePrice;
	private final Map<String, Map<String, Integer>> wallet;

	@Builder
	private WalletResult(
			@NonNull UUID person,
			@NonNull String type, 
			@NonNull String stock, 
			@NonNull Integer sumQuantity,
			@NonNull BigDecimal avgPrice, 
			@NonNull BigDecimal avgBrokeragePrice,
			@NonNull Map<String, Map<String, Integer>> wallet) {
		this.person = person;
		this.type = type;
		this.stock = stock;
		this.sumQuantity = sumQuantity;
		this.avgPrice = avgPrice;
		this.avgBrokeragePrice = avgBrokeragePrice;
		this.wallet = wallet;
	}

}
