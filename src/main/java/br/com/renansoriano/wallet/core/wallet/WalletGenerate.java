package br.com.renansoriano.wallet.core.wallet;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import br.com.renansoriano.wallet.core.order.Order;
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
		
		List<Order> orders = repository.findByPerson(request.getPerson());
		
		Map<String, Map<String, BigDecimal>> ordersGrouped =
			    orders.stream().collect(
			    		Collectors.groupingBy(Order::getStock, 
			    		Collectors.groupingBy(Order::getType, 
			    		Collectors.reducing(BigDecimal.ZERO, Order::getPrice, BigDecimal::add)
			    		))
		);
			    
			    /*
			    .entrySet()
			    .stream()
			    .flatMap(e1 -> e1.getValue()
			    		.entrySet()
			    		.stream()
			    		.map(e2 -> new Order(e2.getValue(), e2.getValue(), e2.getValue())
			    		.collect(Collectors.toList())));*/
		
		
		return WalletResult.builder()
				.type("BUY")
				.stock("PETR4")
				.sumQuantity(30)
				.avgPrice(new BigDecimal(32))
				.avgBrokeragePrice(new BigDecimal(1))
				.teste(ordersGrouped)
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
	
	private BigDecimal average(List<BigDecimal> bigDecimals, RoundingMode roundingMode) {
	    BigDecimal sum = bigDecimals.stream()
	        .map(Objects::requireNonNull)
	        .reduce(BigDecimal.ZERO, BigDecimal::add);
	    return sum.divide(new BigDecimal(bigDecimals.size()), roundingMode);
	}

}
