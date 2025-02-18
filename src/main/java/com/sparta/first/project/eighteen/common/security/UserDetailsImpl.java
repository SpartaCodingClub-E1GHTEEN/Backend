package com.sparta.first.project.eighteen.common.security;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sparta.first.project.eighteen.model.users.Users;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

	private final Users users;

	public Users getUsers() {
		return users;
	}

	@Override
	public String getUsername() {
		return users.getUsername();
	}

	@Override
	public String getPassword() {
		return users.getUserPassword();
	}

	public UUID getUserUUID() {
		return users.getUserId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(users.getRole().getAuthority()));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
