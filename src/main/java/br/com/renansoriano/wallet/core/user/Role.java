package br.com.renansoriano.wallet.core.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Role {

	private final Integer id;
	private ERole name;

	@Builder
	private Role(
			@NonNull Integer id,
			@NonNull ERole name) {
		this.id =id;
		this.name = name;
	}
	
	public enum ERole {
		ROLE_USER,
	    ROLE_MODERATOR,
	    ROLE_ADMIN
	}
}