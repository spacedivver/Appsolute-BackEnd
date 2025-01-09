package com.blaybus.appsolute.leaderquest.controller;

import com.blaybus.appsolute.leaderquest.domain.entity.LeQuestBoard;
import com.blaybus.appsolute.leaderquest.service.LeQuestBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LeQuestBoardController {
    private final LeQuestBoardService service;

    @GetMapping("/le-quest-boards")
    public ResponseEntity<List<LeQuestBoard>> getLeQuestBoards(
            @RequestParam Long userId,
            @RequestParam Integer period) {
        List<LeQuestBoard> result = service.getLeQuestBoards(userId, period);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/import-le-quest-boards")
    public ResponseEntity<String> importLeQuestBoards(@RequestParam String range) {
        try {
            service.importLeQuestBoards(range);
            return ResponseEntity.ok("Quest boards imported successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to import quest boards: " + e.getMessage());
        }
    }

}
