package com.blaybus.appsolute.fcm.service;

import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
import com.blaybus.appsolute.fcm.domain.FCMMessageDto;
import com.blaybus.appsolute.fcm.domain.entity.NotificationLogs;
import com.blaybus.appsolute.fcm.repository.JpaNotificationLogRepository;
import com.blaybus.appsolute.user.domain.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final ObjectMapper objectMapper;
    private final JpaNotificationLogRepository notificationLogRepository;

    @Value("${firebase.message.api.url}")
    private String API_URL;

    private String getAccessToken() throws IOException {

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(
                        "/firebase/blaybus-appsolute-firebase-adminsdk.json").getInputStream())
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();

        return googleCredentials.getAccessToken().getTokenValue();
    }

    public Boolean sendMessageTo(User user, String targetToken, String title, String body, String image) {

        String message = makeMessage(targetToken, title, body, image);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=UTF-8"));

        Request request = null;
        try {
            request = new Request.Builder()
                    .url(API_URL)
                    .post(requestBody)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                    .header(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                    .build();
        } catch (IOException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("fcm 요청 생성 중 오류가 발생하였습니다.", 500, LocalDateTime.now()));
        }

        boolean result = false;

        try (Response response = client.newCall(request).execute()){
            result = response.isSuccessful();
        } catch (Exception e) {
            log.error("FCM sendMessageTo error:" + LocalDateTime.now() + ", msg: " + e.getMessage());
        }

        if(result) {
            NotificationLogs notificationLogs = NotificationLogs.builder()
                    .notificationTitle(title)
                    .notificationContent(body)
                    .user(user)
                    .build();

            notificationLogRepository.save(notificationLogs);
        }

        return result;
    }

    private String makeMessage(String targetToken, String title, String body, String image)  {
        FCMMessageDto fcmMessageDto = FCMMessageDto.builder()
                .message(
                        FCMMessageDto.Message.builder()
                                .token(targetToken)
                                .notification(
                                        FCMMessageDto.Notification.builder()
                                                .title(title)
                                                .body(body)
                                                .image(image)
                                                .build()
                                )
                                .build()
                )
                .validateOnly(false)
                .build();

        try {
            return objectMapper.writeValueAsString(fcmMessageDto);
        } catch (JsonProcessingException e) {
            throw new ApplicationException(ErrorStatus.toErrorStatus("json 변경 중 오류가 발생하였습니다.", 500, LocalDateTime.now()));
        }
    }
}
