package com.blaybus.appsolute.fcm.service;

import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
import com.blaybus.appsolute.fcm.domain.entity.FcmToken;
import com.blaybus.appsolute.fcm.domain.request.CreateFcmTokenRequest;
import com.blaybus.appsolute.fcm.domain.response.ReadFcmTokenResponse;
import com.blaybus.appsolute.fcm.repository.JpaFcmTokenRepository;
import com.blaybus.appsolute.user.domain.entity.User;
import com.blaybus.appsolute.user.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FcmTokenService {

    private final JpaFcmTokenRepository tokenRepository;
    private final JpaUserRepository userRepository;

    public void createToken(Long userId, CreateFcmTokenRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 유저가 없습니다.", 404, LocalDateTime.now())
                ));

        tokenRepository.findByFcmTokenContent(request.fcmToken()).orElseGet(
                () -> tokenRepository.save(request.toEntity(user)));
    }

    public List<ReadFcmTokenResponse> getFcmTokens(Long userId) {
        return tokenRepository.findByUser_id(userId).stream().map(ReadFcmTokenResponse::fromEntity).toList();
    }
}
