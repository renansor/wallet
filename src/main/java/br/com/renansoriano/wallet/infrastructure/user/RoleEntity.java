package br.com.renansoriano.wallet.infrastructure.user;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;
	
	public Role toRole() {
		return Role.builder()
				.id(id)
				.name(name)
				.build();
	}
	
	public static RoleEntity of(Role role) {
		RoleEntity entity = new RoleEntity();
		entity.setId(role.getId());
		entity.setName(role.getName());
		
		return entity;
	}
	
	public static Set<RoleEntity> of(Set<Role> roles) {
		return roles.stream().map(RoleEntity::of).collect(Collectors.toSet());
	}
}