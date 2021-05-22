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

	private final UUID userId;
	private final ZonedDateTime buyDate;
	
	@Builder(builderMethodName = "walletWithDate")
	private WalletRequest(
			@NonNull UUID userId, 
			@NonNull ZonedDateTime buyDate) {
		this.userId = userId;
		this.buyDate = buyDate;
	}
	
	@Builder(builderMethodName = "walletWithoutDate")
	private WalletRequest(
			@NonNull UUID userId) {
		this.userId = userId;
		this.buyDate = null;
	}
}
