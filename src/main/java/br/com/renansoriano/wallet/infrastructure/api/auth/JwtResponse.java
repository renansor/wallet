package br.com.renansoriano.wallet.infrastructure.api.auth;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class JwtResponse {
	
	private String token;
	private String type;
	private UUID id;
	private String username;
	private String mobile;
	private String email;
	private String document;
	private List<String> roles;
	
	@Builder
	private JwtResponse(
			@NonNull String token,
			@NonNull String type, 
			@NonNull UUID id, 
			@NonNull String username,
			@NonNull String mobile,
			@NonNull String email, 
			@NonNull String document,
			@NonNull List<String> roles) {
		this.token = token;
		this.type = type;
		this.id = id;
		this.username = username;
		this.mobile = mobile;
		this.email = email;
		this.document = document;
		this.roles = roles;
	}	
}