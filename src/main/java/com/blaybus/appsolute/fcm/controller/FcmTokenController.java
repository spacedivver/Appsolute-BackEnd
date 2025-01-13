package com.blaybus.appsolute.fcm.controller;

import com.blaybus.appsolute.commons.annotation.Authenticated;
import com.blaybus.appsolute.departmentgroupquest.domain.response.ReadDepartmentGroupQuestResponse;
import com.blaybus.appsolute.fcm.domain.request.CreateFcmTokenRequest;
import com.blaybus.appsolute.fcm.domain.response.ReadFcmTokenResponse;
import com.blaybus.appsolute.fcm.service.FcmTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "FcmToken API", description = "FcmToken API")
public class FcmTokenController {

    private final FcmTokenService fcmTokenService;

    @Operation(summary = "fcm token을 생성합니다.", description = "fcm token을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @PostMapping
    @Authenticated
    public ResponseEntity<Void> createFcmToken(HttpServletRequest req, @RequestBody CreateFcmTokenRequest request) {
        fcmTokenService.createToken(Long.parseLong(req.getAttribute("id").toString()), request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "유저의 fcmToken들을 가져옵니다.", description = "유저의 fcmToken들을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "직무 퀘스트 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping
    @Authenticated
    public ResponseEntity<List<ReadFcmTokenResponse>> getFcmToken(HttpServletRequest req) {
        return ResponseEntity.ok(fcmTokenService.getFcmTokens(Long.parseLong(req.getAttribute("id").toString())));
    }
}
