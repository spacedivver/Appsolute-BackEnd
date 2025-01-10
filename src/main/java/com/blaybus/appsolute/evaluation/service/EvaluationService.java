package com.blaybus.appsolute.evaluation.service;

import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
import com.blaybus.appsolute.evaluation.domain.entity.Evaluation;
import com.blaybus.appsolute.evaluation.domain.entity.EvaluationGrade;
import com.blaybus.appsolute.evaluation.domain.request.CreateEvaluationRequest;
import com.blaybus.appsolute.evaluation.domain.request.DeleteEvaluationRequest;
import com.blaybus.appsolute.evaluation.domain.type.GradeType;
import com.blaybus.appsolute.evaluation.domain.type.PeriodType;
import com.blaybus.appsolute.evaluation.repository.JpaEvaluationGradeRepository;
import com.blaybus.appsolute.evaluation.repository.JpaEvaluationRepository;
import com.blaybus.appsolute.user.domain.entity.User;
import com.blaybus.appsolute.user.repository.JpaUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluationService {

    private final JpaEvaluationRepository evaluationRepository;
    private final JpaEvaluationGradeRepository evaluationGradeRepository;
    private final JpaUserRepository userRepository;

    public void createOrUpdateEvaluation(CreateEvaluationRequest request) {
        Integer year = LocalDateTime.now().getYear();
        GradeType gradeType = GradeType.valueOf(request.grade().replace("등급", ""));
        String employeeNumber = request.employeeNumber();
        PeriodType period = PeriodType.valueOf(request.period());

        User user = userRepository.findByEmployeeNumber(employeeNumber)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 유저가 없습니다.", 404, LocalDateTime.now())
                ));

        EvaluationGrade evaluationGrade = evaluationGradeRepository.findByEvaluationGradeName(gradeType)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 등급이 없습니다.", 404, LocalDateTime.now())
                ));

        Evaluation evaluation = evaluationRepository.findByUserAndYear(user, year)
                        .orElseGet(() -> evaluationRepository.save(Evaluation.builder()
                                .year(year)
                                .user(user)
                                .periodType(period)
                                .evaluationGrade(evaluationGrade)
                                .build()));

        evaluation.updateEvaluationGrade(evaluationGrade);
    }

    public void deleteEvaluation(DeleteEvaluationRequest request) {

        User user = userRepository.findByEmployeeNumber(request.employeeNumber())
                        .orElseThrow(() -> new ApplicationException(
                                ErrorStatus.toErrorStatus("해당하는 사용자가 없습니다.", 404, LocalDateTime.now())
                        ));

        evaluationRepository.deleteByUserAndYear(user, LocalDateTime.now().getYear());
    }
}
