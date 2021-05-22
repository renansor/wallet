package br.com.renansoriano.wallet.infrastructure.api;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

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
	
	@JsonProperty("id")
	private UUID id;
	
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
	
	@JsonProperty("created_at")
	private ZonedDateTime createdAt;
	
	@JsonProperty("aboutMe")
	private String aboutMe;
	
	@JsonProperty("profile_photo")
	private String profilePhoto;
}
