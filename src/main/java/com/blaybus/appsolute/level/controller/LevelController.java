package com.blaybus.appsolute.level.controller;

import com.blaybus.appsolute.departmentgroupquest.domain.response.ReadDepartmentGroupQuestResponse;
import com.blaybus.appsolute.level.domain.request.CreateLevelRequest;
import com.blaybus.appsolute.level.domain.request.DeleteLevelRequest;
import com.blaybus.appsolute.level.domain.response.ReadLevelResponse;
import com.blaybus.appsolute.level.service.LevelService;
import com.blaybus.appsolute.user.domain.request.UpdateLevelRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/levels")
@Tag(name = "레벨 API", description = "레벨 API")
public class LevelController {

    private final LevelService levelService;

    @Operation(summary = "전체 레벨을 가져옵니다.", description = "전체 레벨을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전체 레벨 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ReadLevelResponse>> getAllLevels() {
        return ResponseEntity.ok(levelService.getAllLevels());
    }

    @PostMapping
    public ResponseEntity<Void> createLevel(@RequestBody CreateLevelRequest request) {
        levelService.createLevel(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteLevel(@RequestBody DeleteLevelRequest request) {
        levelService.deleteLevel(request);
        return ResponseEntity.noContent().build();
    }
}
