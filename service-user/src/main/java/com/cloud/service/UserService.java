package com.cloud.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com.cloud.model.UserEntity;
import com.cloud.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService{

    private final UserRepository userRepository;

    @Override
    public Optional<UserEntity> create(UserEntity user) throws Exception {
        try {
            UserEntity userAdded = this.userRepository.save(user);
            return Optional.of(userAdded);
        } catch (Exception error) {
            throw new Exception("Error to create user: " + error.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public Optional<UserEntity> getById(Long id) throws Exception {
        try {
            Optional<UserEntity> userOp = this.userRepository.findById(id);
            return userOp;
        } catch (Exception error) {
            throw new Exception("Error to create user: " + error.getMessage());
        }
    }

    @Override
    public Optional<UserEntity> getByName(String name) throws Exception {
        try {
            Optional<UserEntity> userOp = this.userRepository.findByName(name);
            return Optional.of(userOp.get());
        } catch (Exception error) {
            throw new Exception("Error to create user: " + error.getMessage());
        }
    }

}
