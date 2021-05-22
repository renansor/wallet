package br.com.renansoriano.wallet.infrastructure.api;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.renansoriano.wallet.core.order.Order;
import br.com.renansoriano.wallet.core.user.User;
import br.com.renansoriano.wallet.core.wallet.WalletGenerate;
import br.com.renansoriano.wallet.core.wallet.WalletRequest;
import br.com.renansoriano.wallet.core.wallet.WalletResult;
import br.com.renansoriano.wallet.infrastructure.order.JpaOrderRepository;
import br.com.renansoriano.wallet.infrastructure.user.JpaUserRepository;

@RestController
public class Controller {

	private final JpaOrderRepository jpaOrderRepository;
	private final JpaUserRepository jpaUserRepository;
	private final WalletGenerate walletGenerate;

	public Controller(
			JpaOrderRepository jpaOrderRepository,
			JpaUserRepository jpaUserRepository,
			WalletGenerate walletGenerate) {
		this.jpaOrderRepository = jpaOrderRepository;
		this.jpaUserRepository = jpaUserRepository;
		this.walletGenerate = walletGenerate;
	}

	@GetMapping("/orders/{person}")
	public List<Order> get(
			@PathVariable("userId") UUID userId,
			@RequestParam(name="buyDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime buyDate) {
		return jpaOrderRepository.findByUserId(userId);
	}
	
	@PostMapping("/orders")
	public Order post(@Valid @RequestBody RequestOrder request) {
		
		Order order = Order.builder()
				.id(request.getId())
				.userId(request.getUserId())
				.buyDate(request.getBuyDate())
				.type(request.getType())
				.financialInstitute(request.getFinancialInstitute())
				.stock(request.getStock())
				.quantity(request.getQuantity())
				.price(request.getPrice())
				.brokeragePrice(request.getBrokeragePrice())
				.build();
		
		jpaOrderRepository.save(order);
		
		return order;
	}
	
	@GetMapping("/wallets")
	public WalletResult getWallet(
			@RequestParam(name="userId") UUID userId, 
			@RequestParam(name="buyDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime buyDate) {
		
		WalletRequest request = WalletRequest.walletWithDate()
		.userId(userId)
		.buyDate(buyDate)
		.build();
		
		return walletGenerate.generateWalletWithDate(request);
	}
	
	@GetMapping("/users")
	public List<User> get(
			@PathVariable("userId") UUID userId) {
		
		return jpaUserRepository.findByUserId(userId);
	}
	
	@PostMapping("/users")
	public User post(@Valid @RequestBody RequestUser request) {
		
		
		User user = User.builder()
				.id(UUID.randomUUID())
				.name(request.getName())
				.lastName(request.getLastName())
				.birthday(request.getBirthday())
				.email(request.getEmail())
				.mobile(request.getMobile())
				.document(request.getDocument())
				.password(request.getPassword())
				.createdAt(request.getCreatedAt())
				.aboutMe(request.getAboutMe())
				.profilePhoto(request.getProfilePhoto())
				.build();
		
		jpaUserRepository.save(user);
		
		return user;
	}
	
	@DeleteMapping("users/{idUser}")
	public String delete (@PathVariable("userId") UUID userId) {
		
		jpaUserRepository.delete(userId);
		
		return "user successfully deleted";
	}
} 