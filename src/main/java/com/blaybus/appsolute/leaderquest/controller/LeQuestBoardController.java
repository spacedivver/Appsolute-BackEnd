package com.blaybus.appsolute.leaderquest.controller;

import com.blaybus.appsolute.leaderquest.domain.entity.LeQuestBoard;
import com.blaybus.appsolute.leaderquest.domain.request.LeQuestBoardRequest;
import com.blaybus.appsolute.leaderquest.service.LeQuestBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/le-Leader-board")
@Tag(name="개인별 리더 퀘스트 현황 정보 api")
public class LeQuestBoardController {
    private final LeQuestBoardService service;

    @Operation(summary = "개인별 리더 퀘스트 현황 정보 조회")
    @GetMapping("/get")
    public ResponseEntity<List<LeQuestBoard>> getLeQuestBoard(
            @RequestParam Long userId,
            @RequestParam Integer period) {
        List<LeQuestBoard> result = service.getLeQuestBoard(userId, period);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "개인별 리더 퀘스트 현황을 저장")
    @GetMapping("/save")
    public ResponseEntity<String> saveLeQuestBoard(@RequestBody LeQuestBoardRequest leQuestBoardRequest) {
        try {
            service.saveLeQuestBoard(leQuestBoardRequest);
            return ResponseEntity.ok("성공적으로 저장되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to import quest boards: " + e.getMessage());
        }
    }

}
