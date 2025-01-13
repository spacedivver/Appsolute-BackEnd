package com.blaybus.appsolute.fcm.domain.entity;

import com.blaybus.appsolute.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "NOTIFICATION_LOGS")
@EntityListeners(AuditingEntityListener.class)
public class NotificationLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_logs_id")
    private Long notificationLogId;

    @Column(name = "notification_title")
    private String notificationTitle;

    @Column(name = "notification_content", columnDefinition = "TEXT")
    private String notificationContent;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public NotificationLogs(Long notificationLogId, String notificationTitle, String notificationContent, LocalDateTime createdAt, User user) {
        this.notificationLogId = notificationLogId;
        this.notificationTitle = notificationTitle;
        this.notificationContent = notificationContent;
        this.createdAt = createdAt;
        this.user = user;
    }
}
