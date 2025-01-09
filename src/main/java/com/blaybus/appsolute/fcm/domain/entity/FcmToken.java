package com.blaybus.appsolute.fcm.domain.entity;

import com.blaybus.appsolute.fcm.domain.type.DeviceType;
import com.blaybus.appsolute.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "FCM_TOKEN")
public class FcmToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fcm_token_id")
    private Long fcmTokenId;

    @Column(name = "fcm_token_content")
    private String fcmTokenContent;

    @Column(name = "device_type")
    private DeviceType deviceType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public FcmToken(Long fcmTokenId, String fcmTokenContent, DeviceType deviceType, User user) {
        this.fcmTokenId = fcmTokenId;
        this.fcmTokenContent = fcmTokenContent;
        this.deviceType = deviceType;
        this.user = user;
    }
}
