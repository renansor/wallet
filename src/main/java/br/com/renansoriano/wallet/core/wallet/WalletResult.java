package br.com.renansoriano.wallet.core.wallet;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class WalletResult {

	private final String type;
	private final String stock;
	private final Integer sumQuantity;
	private final BigDecimal avgPrice;
	private final BigDecimal avgBrokeragePrice;

	@Builder
	private WalletResult(
			@NonNull String type, 
			@NonNull String stock, 
			@NonNull Integer sumQuantity,
			@NonNull BigDecimal avgPrice, 
			@NonNull BigDecimal avgBrokeragePrice) {
		this.type = type;
		this.stock = stock;
		this.sumQuantity = sumQuantity;
		this.avgPrice = avgPrice;
		this.avgBrokeragePrice = avgBrokeragePrice;
	}

}
