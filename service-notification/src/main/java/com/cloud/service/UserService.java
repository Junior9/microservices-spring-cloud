package com.cloud.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cloud.exceptions.UserNotFoundException;
import com.cloud.response.ResponseApi;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;
    private final String URL = "http://SERVICE-USER/api/user/";

    public ResponseApi getUser(Long id) throws Exception{
        try {
            ResponseApi response = this.restTemplate.getForObject(URL+id, ResponseApi.class);
            if(Objects.nonNull(response) && response.getMessage().equals("Success")){
                return response;
            }
            throw new UserNotFoundException("User not found");
        } catch (Exception error) {
            throw new Exception("Error to get user:  " + error.getMessage());
        }
    }

}
