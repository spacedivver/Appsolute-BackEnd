package com.blaybus.appsolute.leaderquest.controller;

import com.blaybus.appsolute.commons.annotation.Authenticated;
import com.blaybus.appsolute.leaderquest.domain.request.LeQuestBoardRequest;
import com.blaybus.appsolute.leaderquest.domain.response.LeQuestBoardResponse;
import com.blaybus.appsolute.leaderquest.service.LeQuestBoardService;
import com.blaybus.appsolute.user.domain.response.ReadUserResponse;
import com.blaybus.appsolute.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
    private final UserService userService;

    @Operation(summary = "개인별 리더 퀘스트 현황 정보 조회")
    @Authenticated
    @GetMapping("")
    public ResponseEntity<List<LeQuestBoardResponse>> getLeQuestBoard(HttpServletRequest req, @RequestParam Long month) {
        List<LeQuestBoardResponse> result = service.getLeQuestBoard(Long.parseLong(req.getAttribute("id").toString()), month);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "개인별 리더 퀘스트 현황을 저장")
    @PostMapping("/save")
    public ResponseEntity<Void> updateLeQuestBoard(@RequestBody LeQuestBoardRequest leQuestBoardRequest) {
        service.updateLeQuestBoard(leQuestBoardRequest);
        return ResponseEntity.ok().build();
    }

}
