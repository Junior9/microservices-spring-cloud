package com.cloud.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.model.UserEntity;
import com.cloud.response.ResponseApi;
import com.cloud.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<ResponseApi> create(@RequestBody UserEntity userE){
        try {
            Optional<UserEntity> user = this.userService.create(userE);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(user.get()).build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error").build());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseApi> getById(@PathVariable Long id){
        try {
            Optional<UserEntity> user = this.userService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(user.get()).build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error").build());
        }
    }

    @GetMapping("intentity/{name}")
    public ResponseEntity<ResponseApi> getByUserName(@PathVariable String name){
        try {
            Optional<UserEntity> user = this.userService.getByName(name);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(user.get()).build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error").build());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseApi> delete(@PathVariable Long id){
        try {
            this.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error").build());
        }
    }
}
