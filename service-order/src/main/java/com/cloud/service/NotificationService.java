package com.cloud.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cloud.dto.SendEmailDto;
import com.cloud.exceptions.CreateException;
import com.cloud.response.ResponseApi;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final RestTemplate restTemplate;
    private final String URL = "http://SERVICE-NOTIFICATION/api/notification/";

    public ResponseApi createNotification(Long id) throws Exception{
        try {
            SendEmailDto sendEmailDto = SendEmailDto.builder().userId(id).message("Order created").build();
            ResponseApi response = this.restTemplate.postForObject(URL, sendEmailDto, ResponseApi.class);
            if(Objects.nonNull(response) && response.getMessage().equals("Success")){
                return response;
            }
            throw new CreateException("Send email order created");
        } catch (Exception error) {
            throw new Exception("Error to send notification:  " + error.getMessage());
        }
    }

}
