package com.blaybus.appsolute.fcm.domain.response;

import com.blaybus.appsolute.fcm.domain.entity.FcmToken;
import com.blaybus.appsolute.fcm.domain.type.DeviceType;
import lombok.Builder;

@Builder
public record ReadFcmTokenResponse(
        DeviceType deviceType,
        String fcmToken
) {
    public static ReadFcmTokenResponse fromEntity(FcmToken token) {
        return ReadFcmTokenResponse.builder()
                .deviceType(token.getDeviceType())
                .fcmToken(token.getFcmTokenContent())
                .build();
    }
}
