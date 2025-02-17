package com.sparta.first.project.eighteen.domain.users;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.first.project.eighteen.model.users.User;

public interface UserRepository extends JpaRepository<User, UUID> {
}
