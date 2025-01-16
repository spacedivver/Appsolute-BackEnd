package com.blaybus.appsolute.evaluation.controller;

import com.blaybus.appsolute.commons.annotation.Authenticated;
import com.blaybus.appsolute.evaluation.domain.request.ReadEvaluationRequest;
import com.blaybus.appsolute.evaluation.domain.response.ReadEvaluationResponse;
import com.blaybus.appsolute.evaluation.domain.response.ReadGradeResponse;
import com.blaybus.appsolute.evaluation.service.EvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation")
@Tag(name = "인사평가 API", description = "인사평가 API")
public class EvaluationController {

    private final EvaluationService evaluationService;

    @Operation(summary = "해당 유저의 인사평가를 가져옵니다.", description = "해당 유저의 인사평가를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "해당 유저의 인사평가 조회 성공", content = @Content(schema = @Schema(implementation = ReadEvaluationResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @Authenticated
    @GetMapping
    public ResponseEntity<ReadEvaluationResponse> getEvaluation(HttpServletRequest req, ReadEvaluationRequest request) {
        return ResponseEntity.ok(evaluationService.getEvaluation(Long.parseLong(req.getAttribute("id").toString()), request));
    }
}
