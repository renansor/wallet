package br.com.renansoriano.wallet.infrastructure.user;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import br.com.renansoriano.wallet.core.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users",
uniqueConstraints = { 
		@UniqueConstraint(columnNames = "user_name"),
		@UniqueConstraint(columnNames = "email"),
		@UniqueConstraint(columnNames = "mobile"),
		@UniqueConstraint(columnNames = "document")
	})
public class UserEntity {

	@Id
	@Column(name = "id", nullable = false)
	@Type(type = "uuid-binary")
	private UUID id;
	
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "user_name", nullable = false)
	private String userName;
		
	@Column(name = "birthday", nullable = false)
	private LocalDate birthday;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "mobile", nullable = false)
	private String mobile;

	@Column(name = "document", nullable = false)
	private String document;	

	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "created_at", nullable = false)
	private ZonedDateTime createdAt;

	@Column(name = "about_me", nullable = false)
	private String aboutMe;
	
	@Column(name = "profile_photo", nullable = false)
	private String profilePhoto;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<RoleEntity> roles = new HashSet<>();
	
	public User toUser() {
		
		return User.builder()
				.id(id)
				.name(name)
				.lastName(lastName)
				.userName(userName)
				.birthday(birthday)
				.email(email)
				.mobile(mobile)
				.document(document)
				.password(password)
				.createdAt(createdAt)
				.aboutMe(aboutMe)
				.profilePhoto(profilePhoto)
				.roles(roles.stream().map(RoleEntity::toRole).collect(Collectors.toSet()))
				.build();
	}
	

	public static UserEntity of(User user) {

		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		entity.setName(user.getName());
		entity.setLastName(user.getLastName());
		entity.setUserName(user.getUserName());
		entity.setBirthday(user.getBirthday());
		entity.setEmail(user.getEmail());
		entity.setMobile(user.getMobile());
		entity.setDocument(user.getDocument());
		entity.setPassword(user.getPassword());
		entity.setCreatedAt(user.getCreatedAt());
		entity.setAboutMe(user.getAboutMe());
		entity.setProfilePhoto(user.getProfilePhoto());
		entity.setRoles(RoleEntity.of(user.getRoles()));

		return entity;
	}
}
