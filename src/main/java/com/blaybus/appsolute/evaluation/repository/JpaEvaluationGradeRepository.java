package com.blaybus.appsolute.evaluation.repository;

import com.blaybus.appsolute.evaluation.domain.entity.EvaluationGrade;
import com.blaybus.appsolute.evaluation.domain.type.GradeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaEvaluationGradeRepository extends JpaRepository<EvaluationGrade, Long> {
    Optional<EvaluationGrade> findByEvaluationGradeName(GradeType gradeType);
}
