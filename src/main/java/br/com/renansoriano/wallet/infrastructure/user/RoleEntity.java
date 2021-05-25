package br.com.renansoriano.wallet.infrastructure.user;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import br.com.renansoriano.wallet.core.user.Role;
import br.com.renansoriano.wallet.core.user.Role.ERole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class RoleEntity {
	
	@Id
	@Column(name = "id", nullable = false)
	@Type(type = "uuid-binary")
	private UUID id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;

	public RoleEntity() {
	}

	public RoleEntity(ERole name) {
		this.name = name;
	}
	
	public Role toRole() {
		return Role.builder()
				.id(id)
				.name(name)
				.build();
	}
	
	public static Set<RoleEntity> of(Set<Role> roles) {
		return roles.stream().map(role -> new RoleEntity())
				.collect(Collectors.toSet());
	}
}