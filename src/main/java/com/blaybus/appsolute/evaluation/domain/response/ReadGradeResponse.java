package com.blaybus.appsolute.evaluation.domain.response;

import com.blaybus.appsolute.evaluation.domain.entity.EvaluationGrade;
import com.blaybus.appsolute.evaluation.domain.type.GradeType;
import lombok.Builder;

@Builder
public record ReadGradeResponse(
        GradeType gradeName,
        Long xp
) {
    public static ReadGradeResponse fromEntity(EvaluationGrade grade) {
        return ReadGradeResponse.builder()
                .gradeName(grade.getEvaluationGradeName())
                .xp(grade.getEvaluationGradePoint())
                .build();
    }
}
