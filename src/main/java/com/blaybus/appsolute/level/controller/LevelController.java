package com.blaybus.appsolute.level.controller;

import com.blaybus.appsolute.level.domain.response.ReadLevelResponse;
import com.blaybus.appsolute.level.service.LevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
