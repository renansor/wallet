package br.com.renansoriano.wallet.core.wallet;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.com.renansoriano.wallet.core.order.Order;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class WalletResult {
	
	private final UUID userId;
	private final String type;
	private final String stock;
	private final Integer sumQuantity;
	private final BigDecimal avgPrice;
	private final BigDecimal avgBrokeragePrice;
	private final Map<String, List<Order>> wallet;

	@Builder
	private WalletResult(
			@NonNull UUID userId,
			@NonNull String type, 
			@NonNull String stock, 
			@NonNull Integer sumQuantity,
			@NonNull BigDecimal avgPrice, 
			@NonNull BigDecimal avgBrokeragePrice,
			@NonNull Map<String, List<Order>> wallet) {
		this.userId = userId;
		this.type = type;
		this.stock = stock;
		this.sumQuantity = sumQuantity;
		this.avgPrice = avgPrice;
		this.avgBrokeragePrice = avgBrokeragePrice;
		this.wallet = wallet;
	}

}
