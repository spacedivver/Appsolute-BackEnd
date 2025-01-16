package com.blaybus.appsolute.evaluation.controller;

import com.blaybus.appsolute.departmentgroupquest.domain.response.ReadDepartmentGroupQuestResponse;
import com.blaybus.appsolute.evaluation.domain.request.UpdateGradeRequest;
import com.blaybus.appsolute.evaluation.domain.response.ReadGradeResponse;
import com.blaybus.appsolute.evaluation.service.EvaluationGradeService;
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
@RequestMapping("/grade")
@Tag(name = "등급 API", description = "등급 API")
public class EvaluationGradeController {

    private final EvaluationGradeService evaluationGradeService;

    @Operation(summary = "인사평가 전체 등급을 가져옵니다.", description = "인사평가 전체 등급을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인사평가 전체 등급 조회 성공", content = @Content(schema = @Schema(implementation = ReadGradeResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ReadGradeResponse>> getAllGrade() {
        return ResponseEntity.ok(evaluationGradeService.getAllGrades());
    }

    @PostMapping
    public ResponseEntity<Void> updateEvaluationGradePoint(@RequestBody UpdateGradeRequest request) {
        evaluationGradeService.updateGradePoint(request);
        return ResponseEntity.noContent().build();
    }
}
