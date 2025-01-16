package com.blaybus.appsolute.evaluation.controller;

import com.blaybus.appsolute.evaluation.domain.request.UpdateGradeRequest;
import com.blaybus.appsolute.evaluation.service.EvaluationGradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grade")
public class EvaluationGradeController {

    private final EvaluationGradeService evaluationGradeService;

    @PostMapping
    public ResponseEntity<Void> updateEvaluationGradePoint(@RequestBody UpdateGradeRequest request) {
        evaluationGradeService.updateGradePoint(request);
        return ResponseEntity.noContent().build();
    }
}
