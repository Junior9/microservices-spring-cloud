package com.cloud.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.dto.RequestSendEmail;
import com.cloud.response.ResponseApi;
import com.cloud.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notification/")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<ResponseApi> sendEmail(@RequestBody RequestSendEmail request){
        try {
             this.notificationService.sendEmail(request.getUserId(), request.getMessage());
             return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error").data(error.getMessage()).build());
        }   
    }

}
