package com.blaybus.appsolute.evaluation.controller;

import com.blaybus.appsolute.commons.annotation.Authenticated;
import com.blaybus.appsolute.evaluation.domain.request.ReadEvaluationRequest;
import com.blaybus.appsolute.evaluation.domain.response.ReadEvaluationResponse;
import com.blaybus.appsolute.evaluation.service.EvaluationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation")
public class EvaluationController {

    private final EvaluationService evaluationService;

    @Authenticated
    @GetMapping
    public ResponseEntity<ReadEvaluationResponse> getEvaluation(HttpServletRequest req, ReadEvaluationRequest request) {
        return ResponseEntity.ok(evaluationService.getEvaluation(Long.parseLong(req.getAttribute("id").toString()), request));
    }
}
