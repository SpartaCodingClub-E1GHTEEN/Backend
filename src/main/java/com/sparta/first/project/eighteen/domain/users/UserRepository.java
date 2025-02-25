package com.sparta.first.project.eighteen.domain.users;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.first.project.eighteen.model.users.Users;

public interface UserRepository extends JpaRepository<Users, UUID>, UserSearchRepository {
	Optional<Users> findByUsername(String username);

	Optional<Users> findByUserIdAndIsDeletedIsFalse(UUID userId);
}
