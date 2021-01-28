package br.com.renansoriano.wallet.core.wallet;

import java.time.ZonedDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class WalletRequest {

	private final UUID person;
	private final ZonedDateTime buyDate;
	
	@Builder(builderMethodName = "walletWithDate")
	private WalletRequest(
			@NonNull UUID person, 
			@NonNull ZonedDateTime buyDate) {
		this.person = person;
		this.buyDate = buyDate;
	}
	
	@Builder(builderMethodName = "walletWithoutDate")
	private WalletRequest(
			@NonNull UUID person) {
		this.person = person;
		this.buyDate = null;
	}
}
