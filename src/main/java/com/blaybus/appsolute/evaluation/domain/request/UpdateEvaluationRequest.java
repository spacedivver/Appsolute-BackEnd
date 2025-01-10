package com.blaybus.appsolute.evaluation.domain.request;

public record UpdateEvaluationRequest(
        String employeeNumber,
        String employeeName,
        String grade
) {
}
