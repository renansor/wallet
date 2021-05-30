package br.com.renansoriano.wallet.infrastructure.api.user;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.renansoriano.wallet.core.user.Role;
import br.com.renansoriano.wallet.core.user.Role.ERole;
import br.com.renansoriano.wallet.core.user.User;
import br.com.renansoriano.wallet.infrastructure.api.user.requests.RequestUser;
import br.com.renansoriano.wallet.infrastructure.user.JpaRoleRepository;
import br.com.renansoriano.wallet.infrastructure.user.JpaUserRepository;

@RestController
public class UserController {
	
	private final JpaUserRepository jpaUserRepository;
	private final JpaRoleRepository jpaRoleRepository;

	public UserController(
			JpaUserRepository jpaUserRepository,
			JpaRoleRepository jpaRoleRepository) {
		this.jpaUserRepository = jpaUserRepository;
		this.jpaRoleRepository = jpaRoleRepository;
	}
	
	@GetMapping("/users")
	public List<User> getUsers() {
		
		return jpaUserRepository.findAll();
	}
	
	@GetMapping("/users/{userId}")
	public User getUserById(
			@PathVariable(name="userId") UUID userId) {
		
		return jpaUserRepository.findByUserId(userId);
	}
	
	@PostMapping("/users")
	public User saveUser(
			@Valid @RequestBody RequestUser request) {
		
		
		User user = User.builder()
				.id(UUID.randomUUID())
				.name(request.getName())
				.lastName(request.getLastName())
				.userName(request.getUserName())
				.birthday(request.getBirthday())
				.email(request.getEmail())
				.mobile(request.getMobile())
				.document(request.getDocument())
				.password(request.getPassword())
				.createdAt(ZonedDateTime.now())
				.aboutMe(request.getAboutMe())
				.profilePhoto(request.getProfilePhoto())
				//.roles(request.getRoles())
				.build();
		
		jpaUserRepository.save(user);
		
		return user;
	}
	
	@DeleteMapping("users/{userId}")
	public User deleteUser (
			@PathVariable("userId") UUID userId) {
		
		User user = jpaUserRepository.findByUserId(userId);
		
		jpaUserRepository.delete(user);
		
		return user;
	}
	
	@PutMapping("users/{userId}")
	public User updateUser (
			@PathVariable("userId") UUID userId,
			@Valid @RequestBody RequestUser request) {
		
		User user = jpaUserRepository.findByUserId(userId);
		
		Set<String> strRoles = request.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = jpaRoleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = jpaRoleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = jpaRoleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = jpaRoleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		
		User userUpdated = User.builder()
				.id(user.getId())
				.name(request.getName())
				.lastName(request.getLastName())
				.userName(request.getUserName())
				.birthday(request.getBirthday())
				.email(request.getEmail())
				.mobile(request.getMobile())
				.document(request.getDocument())
				.password(request.getPassword())
				.aboutMe(request.getAboutMe())
				.profilePhoto(request.getProfilePhoto())
				.createdAt(user.getCreatedAt())
				.roles(roles)
				.build();
		
		jpaUserRepository.update(userUpdated);		
		
		return userUpdated;
	}
} 