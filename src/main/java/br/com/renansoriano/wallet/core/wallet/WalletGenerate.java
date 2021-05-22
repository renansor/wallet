package br.com.renansoriano.wallet.core.wallet;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.renansoriano.wallet.core.order.Order;
import br.com.renansoriano.wallet.infrastructure.order.JpaOrderRepository;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class WalletGenerate {
	
	private static final Logger logger = LoggerFactory.getLogger(WalletGenerate.class);
	
	private final JpaOrderRepository repository;
	
	@Builder
	public WalletGenerate(@NonNull JpaOrderRepository repository) {
		this.repository = repository;
	}
	
	public WalletResult generateWalletWithDate(WalletRequest request) {
		
		logger.info("Start to generate Wallet by userId {}", request.getUserId());
		
		List<Order> orders = repository.findByUserId(request.getUserId());	
		
		logger.info("Found orders {}", orders);
		
		Map<String, List<Order>> ordersGrouped =
			    orders.stream().collect(Collectors.groupingBy(Order::getStock));
		
		logger.info("Grouped by Stock {}", ordersGrouped);
		
		
		logger.info("Dividr por tipos compra e venda");
		logger.info("Fazer a somatoria, media de acordo com a data de compra e somar quando for buy, subitrair cquando for sell");
		
		WalletResult result = WalletResult.builder()
			.userId(request.getUserId())
			.type("BUY")
			.stock("PETR4")
			.sumQuantity(30)
			.avgPrice(new BigDecimal(32))
			.avgBrokeragePrice(new BigDecimal(1))
			.wallet(ordersGrouped)
			.build();
		
		logger.info("WalletGenerate result {}", orders);
		
		return result;		
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
	
	private BigDecimal average(List<BigDecimal> bigDecimals, RoundingMode roundingMode) {
	    BigDecimal sum = bigDecimals.stream()
	        .map(Objects::requireNonNull)
	        .reduce(BigDecimal.ZERO, BigDecimal::add);
	    return sum.divide(new BigDecimal(bigDecimals.size()), roundingMode);
	}

}
