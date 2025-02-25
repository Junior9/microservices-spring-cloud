package com.cloud.service;

import java.util.Optional;

import com.cloud.model.Notification;

public interface INotificationService {

    public Optional<Notification> sendEmail(Long userId, String message);
    public void getEventNewOrder(String order);

}
