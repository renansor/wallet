package br.com.renansoriano.wallet.core.user;

import java.time.LocalDate;
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
public class User {

	private final UUID id;
	private final String name;
	private final String lastName;
	private final String userName;
	private final LocalDate birthday;
	private final String email;
	private final String mobile;
	private final String document;
	private final String password;
	private final ZonedDateTime createdAt;
	private final String aboutMe;
	private final String profilePhoto;

	@Builder
	private User(
			@NonNull UUID id,
			@NonNull String name,
			@NonNull String lastName,
			@NonNull String userName,
			@NonNull LocalDate birthday,
			@NonNull String email,
			@NonNull String mobile,
			@NonNull String document,
			@NonNull String password,
			@NonNull ZonedDateTime createdAt,
			@NonNull String aboutMe,
			@NonNull String profilePhoto) {
		this.id =id;
		this.name = name;
		this.lastName = lastName;
		this.userName = userName;
		this.birthday = birthday;
		this.email = email;
		this.mobile = mobile;
		this.document = document;
		this.password = password;
		this.createdAt = createdAt;
		this.aboutMe = aboutMe;
		this.profilePhoto = profilePhoto;
	}
}
