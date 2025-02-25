package com.cloud.service;

import java.util.LinkedHashMap;
import java.util.Optional;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cloud.dto.OrderDtoKafka;
import com.cloud.exceptions.SendEmailException;
import com.cloud.model.Notification;
import com.cloud.repository.NotificationRepository;
import com.cloud.response.ResponseApi;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final OrderService orderService;
    private final JavaMailSender mailSender;
    private final String FROM = "moraes.junior6@gmail.com";

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

    @Override
    @KafkaListener(topics = "orders", groupId = "newOrders")
    public void getEventNewOrder(String message) {
    
        try {
            Gson gson = new Gson();
            OrderDtoKafka orderDtoKafka =  gson.fromJson(message, OrderDtoKafka.class);
            ResponseApi responseOrder = this.orderService.getOrderById(orderDtoKafka.getOrderId());
            LinkedHashMap mapOrder  = (LinkedHashMap) responseOrder.getData();

            ResponseApi responseUser = this.userService.getUser(orderDtoKafka.getUserId());
            LinkedHashMap mapUser  = (LinkedHashMap) responseUser.getData();

            String bodyEmail = "You hava a new order status " + mapOrder.get("status") + " total :" + mapOrder.get("total") ;

            SimpleMailMessage messageEmail =  new SimpleMailMessage();
            messageEmail.setFrom(FROM);
            messageEmail.setTo(mapUser.get("email").toString());
            messageEmail.setSubject("New Order");
            messageEmail.setText(bodyEmail);
            this.mailSender.send(messageEmail);     
            System.out.println("Email was send to " + mapUser.get("email"));       
        } catch (Exception error) {
            System.out.println("Error to send email");       
            throw new SendEmailException("Error to send email : " + error.getMessage());
        }




    }

}
