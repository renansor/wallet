package br.com.renansoriano.wallet.infrastructure.api.auth.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SignupRequest {
	
	@JsonProperty("user_name")
	private String username;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("password")
	private String password;

}
