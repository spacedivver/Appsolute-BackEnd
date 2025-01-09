package com.blaybus.appsolute.fcm.controller;

import com.blaybus.appsolute.commons.annotation.Authenticated;
import com.blaybus.appsolute.fcm.domain.request.CreateFcmTokenRequest;
import com.blaybus.appsolute.fcm.domain.response.ReadFcmTokenResponse;
import com.blaybus.appsolute.fcm.service.FcmTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fcmToken")
@Slf4j
public class FcmTokenController {

    private final FcmTokenService fcmTokenService;

    @PostMapping
    @Authenticated
    public ResponseEntity<Void> createFcmToken(HttpServletRequest req, @RequestBody CreateFcmTokenRequest request) {
        fcmTokenService.createToken(Long.parseLong(req.getAttribute("id").toString()), request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Authenticated
    public ResponseEntity<List<ReadFcmTokenResponse>> getFcmToken(HttpServletRequest req) {
        return ResponseEntity.ok(fcmTokenService.getFcmTokens(Long.parseLong(req.getAttribute("id").toString())));
    }
}
