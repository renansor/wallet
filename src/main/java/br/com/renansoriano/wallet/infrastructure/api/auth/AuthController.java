package br.com.renansoriano.wallet.infrastructure.api.auth;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.renansoriano.wallet.core.user.Role;
import br.com.renansoriano.wallet.core.user.Role.ERole;
import br.com.renansoriano.wallet.core.user.User;
import br.com.renansoriano.wallet.infrastructure.api.auth.requests.LoginRequest;
import br.com.renansoriano.wallet.infrastructure.api.user.requests.RequestUser;
import br.com.renansoriano.wallet.infrastructure.security.JwtUtils;
import br.com.renansoriano.wallet.infrastructure.security.UserDetailsImpl;
import br.com.renansoriano.wallet.infrastructure.user.JpaRoleRepository;
import br.com.renansoriano.wallet.infrastructure.user.JpaUserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final JpaUserRepository jpaUserRepository;
	
	private final JpaRoleRepository jpaRoleRepository;
	
	private final AuthenticationManager authenticationManager;

	private final PasswordEncoder encoder;

	private final JwtUtils jwtUtils;
	
	public AuthController(
			JpaUserRepository jpaUserRepository, 
			JpaRoleRepository jpaRoleRepository,
			AuthenticationManager authenticationManager,
			PasswordEncoder encoder, 
			JwtUtils jwtUtils) {
		this.jpaUserRepository = jpaUserRepository;
		this.jpaRoleRepository = jpaRoleRepository;
		this.authenticationManager = authenticationManager;
		this.encoder = encoder;
		this.jwtUtils = jwtUtils;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		JwtResponse jwtResponse = JwtResponse.builder()
				.token(jwt)
				.type("Bearer")
				.id(userDetails.getId())
				.username(userDetails.getUsername())
				.mobile(userDetails.getMobile())
				.document(userDetails.getDocument())
				.email(userDetails.getEmail())
				.roles(roles)
				.build();

		return ResponseEntity.ok(jwtResponse);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RequestUser request) {
		
		if (jpaUserRepository.existsByUsername(request.getUserName())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
		
		if (jpaUserRepository.existsByEmail(request.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already taken!"));
		}
		
		if (jpaUserRepository.existsByMobile(request.getMobile())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Mobile is already taken!"));
		}
		
		if (jpaUserRepository.existsByDocument(request.getDocument())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Document is already taken!"));
		}

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
		
		User user = User.builder()
				.id(UUID.randomUUID())
				.name(request.getName())
				.lastName(request.getLastName())
				.userName(request.getUserName())
				.birthday(request.getBirthday())
				.email(request.getEmail())
				.mobile(request.getMobile())
				.document(request.getDocument())
				.password(encoder.encode(request.getPassword()))
				.createdAt(ZonedDateTime.now())
				.aboutMe(request.getAboutMe())
				.profilePhoto(request.getProfilePhoto())
				.roles(roles)
				.build();

		jpaUserRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
