package br.com.renansoriano.wallet.infrastructure.api.user.requests;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class RequestUser {
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("last_name")
	private String lastName;
	
	@JsonProperty("user_name")
	private String userName;
	
	@JsonProperty("birthday")
	private LocalDate birthday;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("mobile")
	private String mobile;
	
	@JsonProperty("document")
	private String document;
	
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("about_me")
	private String aboutMe;
	
	@JsonProperty("profile_photo")
	private String profilePhoto;
	
	@JsonProperty("roles")
	private Set<String> roles;
}
