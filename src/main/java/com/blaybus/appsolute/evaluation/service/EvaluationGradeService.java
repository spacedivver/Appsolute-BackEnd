package com.blaybus.appsolute.evaluation.service;

import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
import com.blaybus.appsolute.evaluation.domain.entity.EvaluationGrade;
import com.blaybus.appsolute.evaluation.domain.request.UpdateGradeRequest;
import com.blaybus.appsolute.evaluation.domain.response.ReadGradeResponse;
import com.blaybus.appsolute.evaluation.repository.JpaEvaluationGradeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EvaluationGradeService {

    private final JpaEvaluationGradeRepository evaluationGradeRepository;

    public void updateGradePoint(UpdateGradeRequest request) {
        EvaluationGrade evaluationGrade = evaluationGradeRepository.findByEvaluationGradeName(request.grade())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 인사평가 등급이 없습니다.", 404, LocalDateTime.now())
                ));

        evaluationGrade.updatePoint(request.gradePoint());
    }

    public List<ReadGradeResponse> getAllGrades() {
        return evaluationGradeRepository.findAll().stream().map(ReadGradeResponse::fromEntity).toList();
    }
}
