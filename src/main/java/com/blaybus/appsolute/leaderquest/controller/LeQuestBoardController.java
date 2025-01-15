package com.blaybus.appsolute.leaderquest.controller;

import com.blaybus.appsolute.leaderquest.domain.request.LeQuestBoardRequest;
import com.blaybus.appsolute.leaderquest.domain.response.LeQuestBoardResponse;
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
    @GetMapping("")
    public ResponseEntity<List<LeQuestBoardResponse>> getLeQuestBoard(
            @RequestParam String userId,
            @RequestParam Long month) {
        List<LeQuestBoardResponse> result = service.getLeQuestBoard(userId, month);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "개인별 리더 퀘스트 현황을 저장")
    @PostMapping("/save")
    public ResponseEntity<Void> updateLeQuestBoard(@RequestBody LeQuestBoardRequest leQuestBoardRequest) {
        service.updateLeQuestBoard(leQuestBoardRequest);
        return ResponseEntity.ok().build();
    }

}
