package com.blaybus.appsolute.fcm.repository;

import com.blaybus.appsolute.fcm.domain.entity.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaFcmTokenRepository extends JpaRepository<FcmToken, Long> {
    List<FcmToken> findByUser_id(Long id);
    Optional<FcmToken> findByFcmTokenContent(String token);
}
