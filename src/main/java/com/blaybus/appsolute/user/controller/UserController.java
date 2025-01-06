package com.blaybus.appsolute.user.controller;

import com.blaybus.appsolute.commons.annotation.Authenticated;
import com.blaybus.appsolute.user.domain.request.LoginUserRequest;
import com.blaybus.appsolute.user.domain.request.UpdateCharacterRequest;
import com.blaybus.appsolute.user.domain.request.UpdatePasswordRequest;
import com.blaybus.appsolute.user.domain.response.LoginUserResponse;
import com.blaybus.appsolute.user.domain.response.ReadUserResponse;
import com.blaybus.appsolute.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<LoginUserResponse> login(LoginUserRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @Authenticated
    @GetMapping
    public ResponseEntity<ReadUserResponse> getUserById(HttpServletRequest req) {
        return ResponseEntity.ok(userService.getUserById(Long.parseLong(req.getAttribute("id").toString())));
    }

    @Authenticated
    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(HttpServletRequest req, UpdatePasswordRequest request) {
        userService.updatePassword(Long.parseLong(req.getAttribute("id").toString()), request);
        return ResponseEntity.ok().build();
    }

    @Authenticated
    @PutMapping("/character")
    public ResponseEntity<Void> changeCharacter(HttpServletRequest req, UpdateCharacterRequest request) {
        userService.updateCharacter(Long.parseLong(req.getAttribute("id").toString()), request);
        return ResponseEntity.ok().build();
    }
}
