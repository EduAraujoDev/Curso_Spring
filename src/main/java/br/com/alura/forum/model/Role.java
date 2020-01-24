package br.com.alura.forum.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final Role ROLE_ADMIN = new Role("ROLE_ADMIN");
	public static final Role ROLE_ALUNO = new Role("ROLE_ALUNO");
	
	@Id
	private String authority;
	
	public Role() {}
	
	@Override
	public String getAuthority() {	
		return this.authority;
	}
	
	
	public Role(String authority) {
		this.authority = authority;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Role role = (Role) obj;
		return Objects.equals(authority, role.authority);
	}
}