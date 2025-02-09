package com.cloud.service;

import java.util.LinkedHashMap;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cloud.exceptions.SendEmailException;
import com.cloud.model.Notification;
import com.cloud.model.UserDto;
import com.cloud.repository.NotificationRepository;
import com.cloud.response.ResponseApi;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {

    private final NotificationRepository notificationRepository;

    private final UserService userService;

    @Override
    public Optional<Notification> sendEmail(Long userId, String message) {
        try {
            ResponseApi response = this.userService.getUser(userId);
            LinkedHashMap map  = (LinkedHashMap) response.getData();
            Notification notification = new Notification();
            notification.setMessage(message);
            notification.setUserId(userId);
            Notification notificationAdded = this.notificationRepository.save(notification);
            System.out.println("Email send to user :" + map.get("name") + " email " + map.get("email") + " message : " + message) ;
            return Optional.of(notificationAdded);
        } catch (Exception error) {
            throw new SendEmailException("Error to send email " + error.getMessage());
        }
    }

}
