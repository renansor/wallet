package br.com.renansoriano.wallet.infrastructure.api;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.renansoriano.wallet.core.order.Order;
import br.com.renansoriano.wallet.core.wallet.WalletGenerate;
import br.com.renansoriano.wallet.core.wallet.WalletRequest;
import br.com.renansoriano.wallet.core.wallet.WalletResult;
import br.com.renansoriano.wallet.infrastructure.order.JpaOrderRepository;

@RestController
public class Controller {

	private final JpaOrderRepository repository;

	private final WalletGenerate walletGenerate;

	public Controller(
			JpaOrderRepository repository,
			WalletGenerate walletGenerate) {
		this.repository = repository;
		this.walletGenerate = walletGenerate;
	}

	@GetMapping("/orders/{person}")
	public List<Order> get(
			@PathVariable("person") UUID person,
			@RequestParam(name="buyDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime buyDate) {
		return repository.findByPerson(person);
	}
	
	@PostMapping("/orders")
	public Order post(@Valid @RequestBody RequestOrder request) {
		
		Order order = Order.builder()
				.id(request.getId())
				.person(request.getPerson())
				.buyDate(request.getBuyDate())
				.type(request.getType())
				.financialInstitute(request.getFinancialInstitute())
				.stock(request.getStock())
				.quantity(request.getQuantity())
				.price(request.getPrice())
				.brokeragePrice(request.getBrokeragePrice())
				.build();
		
		repository.save(order);
		
		return order;
	}
	
	@GetMapping("/wallets")
	public WalletResult getWallet(
			@RequestParam(name="person") UUID person, 
			@RequestParam(name="buyDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime buyDate) {
		
		WalletRequest request = WalletRequest.walletWithDate()
		.person(person)
		.buyDate(buyDate)
		.build();
		
		return walletGenerate.generateWalletWithDate(request);
	}
} 