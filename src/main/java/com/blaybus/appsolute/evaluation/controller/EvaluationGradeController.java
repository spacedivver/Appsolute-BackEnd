package com.blaybus.appsolute.evaluation.controller;

import com.blaybus.appsolute.evaluation.domain.request.UpdateGradeRequest;
import com.blaybus.appsolute.evaluation.domain.response.ReadGradeResponse;
import com.blaybus.appsolute.evaluation.service.EvaluationGradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grade")
public class EvaluationGradeController {

    private final EvaluationGradeService evaluationGradeService;

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
