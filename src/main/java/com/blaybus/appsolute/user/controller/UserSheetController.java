package com.blaybus.appsolute.user.controller;

import com.blaybus.appsolute.user.service.UserSheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserSheetController {

    private final UserSheetService userSheetService;

    //처음에 설정할 때만 사용
    @PostMapping("/usersheet/sync")
    public ResponseEntity<Void> syncUserSheet() {
        userSheetService.syncInitialUserData();
        return ResponseEntity.noContent().build();
    }
}
