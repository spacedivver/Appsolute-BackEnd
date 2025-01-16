package com.blaybus.appsolute.evaluation.domain.request;

import com.blaybus.appsolute.evaluation.domain.type.PeriodType;

public record ReadEvaluationRequest(
        int year,
        PeriodType periodType
) {
}
