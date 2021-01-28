package br.com.renansoriano.wallet.core.wallet;

import java.math.BigDecimal;

import br.com.renansoriano.wallet.infrastructure.order.JpaOrderRepository;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class WalletGenerate {
	
	private final JpaOrderRepository repository;
	
	@Builder
	public WalletGenerate(@NonNull JpaOrderRepository repository) {
		this.repository = repository;
	}
	
	public WalletResult generateWalletWithDate(WalletRequest request) {
		
		return WalletResult.builder()
				.type("BUY")
				.stock("PETR4")
				.sumQuantity(30)
				.avgPrice(new BigDecimal(32))
				.avgBrokeragePrice(new BigDecimal(1))
				.build();		
	}
	
	public WalletResult generateWalletWithoutDate(WalletRequest request) {

		return WalletResult.builder()
				.type("BUY")
				.stock("PETR4")
				.sumQuantity(30)
				.avgPrice(new BigDecimal(32))
				.avgBrokeragePrice(new BigDecimal(1))
				.build();
	}

}
