package com.blaybus.appsolute.fcm.domain;

import com.blaybus.appsolute.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

    @ManyToOne
    private User user;
}
