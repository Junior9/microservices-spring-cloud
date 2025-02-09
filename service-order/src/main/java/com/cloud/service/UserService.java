package com.cloud.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cloud.response.ResponseApi;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;
    private final String URL = "http://SERVICE-USER/api/user/";

    public boolean hasUser(Long userId){
        ResponseApi respobnseApi = this.restTemplate.getForObject(URL+userId, ResponseApi.class);
        return respobnseApi.getMessage().equals("Success");
    }

}
