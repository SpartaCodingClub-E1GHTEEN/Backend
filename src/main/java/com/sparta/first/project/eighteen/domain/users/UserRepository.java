package com.sparta.first.project.eighteen.domain.users;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.first.project.eighteen.model.users.Role;
import com.sparta.first.project.eighteen.model.users.Users;

public interface UserRepository extends JpaRepository<Users, UUID> {
	Optional<Users> findByUsername(String username);

	Page<Users> findAllByRole(Role role, Pageable pageable);

}
