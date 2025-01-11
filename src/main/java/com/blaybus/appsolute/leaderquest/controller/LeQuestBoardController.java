package com.blaybus.appsolute.leaderquest.controller;

import com.blaybus.appsolute.leaderquest.domain.entity.LeQuestBoard;
import com.blaybus.appsolute.leaderquest.domain.request.LeQuestBoardRequest;
import com.blaybus.appsolute.leaderquest.service.LeQuestBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class LeQuestBoardController {
    private final LeQuestBoardService service;

    @GetMapping("/le-quest-boards")
    public ResponseEntity<List<LeQuestBoard>> getLeQuestBoard(
            @RequestParam Long userId,
            @RequestParam Integer period) {
        List<LeQuestBoard> result = service.getLeQuestBoard(userId, period);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/import-le-quest-boards")
    public ResponseEntity<String> saveLeQuestBoard(@RequestBody LeQuestBoardRequest leQuestBoardRequest) {
        try {
            service.saveLeQuestBoard(leQuestBoardRequest);
            return ResponseEntity.ok("성공적으로 저장되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to import quest boards: " + e.getMessage());
        }
    }

}
