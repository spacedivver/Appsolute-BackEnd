package com.blaybus.appsolute.level.controller;

import com.blaybus.appsolute.level.domain.request.CreateLevelRequest;
import com.blaybus.appsolute.level.domain.response.ReadLevelResponse;
import com.blaybus.appsolute.level.service.LevelService;
import com.blaybus.appsolute.user.domain.request.UpdateLevelRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/levels")
public class LevelController {

    private final LevelService levelService;

    @GetMapping
    public ResponseEntity<List<ReadLevelResponse>> getAllLevels() {
        return ResponseEntity.ok(levelService.getAllLevels());
    }

    @PostMapping
    public ResponseEntity<Void> createLevel(@RequestBody CreateLevelRequest request) {
        levelService.createLevel(request);
        return ResponseEntity.noContent().build();
    }
}
