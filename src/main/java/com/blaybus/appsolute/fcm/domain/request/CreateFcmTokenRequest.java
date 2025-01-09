package com.blaybus.appsolute.fcm.domain.request;

import com.blaybus.appsolute.fcm.domain.entity.FcmToken;
import com.blaybus.appsolute.fcm.domain.type.DeviceType;
import com.blaybus.appsolute.user.domain.entity.User;

public record CreateFcmTokenRequest(
        String fcmToken,
        DeviceType deviceType
) {
    public FcmToken toEntity(User user) {
        return FcmToken.builder()
                .fcmTokenContent(fcmToken)
                .deviceType(deviceType)
                .user(user)
                .build();
    }
}
