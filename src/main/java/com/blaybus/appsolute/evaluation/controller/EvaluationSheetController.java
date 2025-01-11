package com.blaybus.appsolute.evaluation.controller;

import com.blaybus.appsolute.evaluation.domain.request.CreateEvaluationRequest;
import com.blaybus.appsolute.evaluation.domain.request.DeleteEvaluationRequest;
import com.blaybus.appsolute.evaluation.service.EvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sheet/evaluation")
@Tag(name = "인사평가 API", description = "인사평가 API")
public class EvaluationSheetController {

    private final EvaluationService evaluationService;

    @Operation(summary = "사번으로 인사평가를 삭제합니다.", description = "사번으로 인사평가를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "직무 퀘스트 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteByEmployeeNumber(@RequestBody DeleteEvaluationRequest request) {
        evaluationService.deleteEvaluation(request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "구글 시트에서 인사평가를 업데이트합니다.", description = "구글 시트에서 인사평가를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "직무 퀘스트 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Void> createOrUpdateEvaluation(@RequestBody CreateEvaluationRequest request) {
        evaluationService.createOrUpdateEvaluation(request);
        return ResponseEntity.ok().build();
    }
}
