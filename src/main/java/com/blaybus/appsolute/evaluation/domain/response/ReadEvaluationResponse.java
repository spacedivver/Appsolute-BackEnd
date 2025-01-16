package com.blaybus.appsolute.evaluation.domain.response;

import com.blaybus.appsolute.evaluation.domain.entity.EvaluationGrade;
import com.blaybus.appsolute.evaluation.domain.type.GradeType;
import lombok.Builder;

@Builder
public record ReadEvaluationResponse(
        GradeType gradeName,
        Long xp
) {
    public static ReadEvaluationResponse fromEvaluationGrade(EvaluationGrade grade) {
        return ReadEvaluationResponse.builder()
                .gradeName(grade.getEvaluationGradeName())
                .xp(grade.getEvaluationGradePoint())
                .build();
    }
}
