package com.blaybus.appsolute.evaluation.domain.request;

import com.blaybus.appsolute.evaluation.domain.type.GradeType;

public record UpdateGradeRequest(
        GradeType grade,
        Long gradePoint
) {
}
