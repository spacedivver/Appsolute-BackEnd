package com.blaybus.appsolute.evaluation.domain.request;

public record CreateEvaluationRequest(
        String employeeNumber,
        String employeeName,
        String grade,
        String period,
        String notes
) {
}
