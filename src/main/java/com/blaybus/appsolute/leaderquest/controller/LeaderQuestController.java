package com.blaybus.appsolute.leaderquest.controller;

import com.blaybus.appsolute.leaderquest.domain.entity.LeaderQuest;
import com.blaybus.appsolute.leaderquest.domain.request.LeaderQuestRequest;
import com.blaybus.appsolute.leaderquest.service.LeaderQuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leader-quest")
@Tag(name="리더 퀘스트 정보 api")
public class LeaderQuestController {

    private LeaderQuestService leaderQuestService;

    @Operation(summary = "리더 퀘스트 전체 정보를 저장")
    @PostMapping
    public ResponseEntity<String> saveLeaderQuest(@RequestBody LeaderQuestRequest leaderQuestRequest) {
        try {
            leaderQuestService.saveLeaderQuest(leaderQuestRequest);
            return ResponseEntity.ok("성공적으로 저장되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 에러 " + e.getMessage());
        }
    }

    @Operation(summary = "리더 퀘스트 전체 정보를 조회")
    @GetMapping
    public ResponseEntity<List<LeaderQuest>> getLeaderQuest() {
        try {
            List<LeaderQuest> result = leaderQuestService.getLeaderQuest();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

}
