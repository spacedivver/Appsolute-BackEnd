package com.blaybus.appsolute.user.controller;

import com.blaybus.appsolute.commons.annotation.Authenticated;
import com.blaybus.appsolute.user.domain.request.LoginUserRequest;
import com.blaybus.appsolute.user.domain.request.UpdateCharacterRequest;
import com.blaybus.appsolute.user.domain.request.UpdatePasswordRequest;
import com.blaybus.appsolute.user.domain.response.LoginUserResponse;
import com.blaybus.appsolute.user.domain.response.ReadUserResponse;
import com.blaybus.appsolute.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
@Tag(name = "유저 API", description = "유저 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "로그인", description = "로그인을 시도합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = LoginUserResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> login(@RequestBody LoginUserRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @Operation(summary = "jwt토큰으로 user정보를 가져옵니다.", description = "jwt 토큰 값으로 user 정보를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 조회 성공", content = @Content(schema = @Schema(implementation = ReadUserResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @Authenticated
    @GetMapping
    public ResponseEntity<ReadUserResponse> getUserById(HttpServletRequest req) {
        return ResponseEntity.ok(userService.getUserById(Long.parseLong(req.getAttribute("id").toString())));
    }

    @Operation(summary = "비밀번호 변경", description = "jwt 토큰, 변경할 password로 비밀번호를 변경합니다..")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @Authenticated
    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(HttpServletRequest req, @RequestBody UpdatePasswordRequest request) {
        userService.updatePassword(Long.parseLong(req.getAttribute("id").toString()), request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "캐릭터 변경", description = "jwt 토큰, 캐릭터id로 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캐릭터 변경 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @Authenticated
    @PutMapping("/character")
    public ResponseEntity<Void> changeCharacter(HttpServletRequest req, @RequestBody UpdateCharacterRequest request) {
        userService.updateCharacter(Long.parseLong(req.getAttribute("id").toString()), request);
        return ResponseEntity.ok().build();
    }
}
