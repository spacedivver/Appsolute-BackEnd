package com.blaybus.appsolute.evaluation.controller;

import com.blaybus.appsolute.evaluation.domain.request.CreateEvaluationRequest;
import com.blaybus.appsolute.evaluation.domain.request.DeleteEvaluationRequest;
import com.blaybus.appsolute.evaluation.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sheet/evaluation")
public class EvaluationSheetController {

    private final EvaluationService evaluationService;

    @DeleteMapping
    public ResponseEntity<Void> deleteByEmployeeNumber(@RequestBody DeleteEvaluationRequest request) {
        evaluationService.deleteEvaluation(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> createOrUpdateEvaluation(@RequestBody CreateEvaluationRequest request) {
        evaluationService.createOrUpdateEvaluation(request);
        return ResponseEntity.ok().build();
    }
}
