package com.cloud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloud.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public Optional<UserEntity> findByName(String name);
}
