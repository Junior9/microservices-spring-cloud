package com.cloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloud.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
