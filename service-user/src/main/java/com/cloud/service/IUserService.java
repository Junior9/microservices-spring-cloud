package com.cloud.service;

import java.util.Optional;

import com.cloud.model.UserEntity;

public interface IUserService {

    public Optional<UserEntity> create(UserEntity user) throws Exception;
    public void delete(Long id);
    public Optional<UserEntity> getByName(String name) throws Exception;
    public Optional<UserEntity> getById(Long id) throws Exception;

}
