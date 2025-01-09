package com.blaybus.appsolute.fcm.repository;

import com.blaybus.appsolute.fcm.domain.entity.NotificationLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNotificationLogRepository extends JpaRepository<NotificationLogs, Long> {
}
