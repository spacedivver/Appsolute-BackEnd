package com.blaybus.appsolute.departmentgroupquest.domain.request;

import com.blaybus.appsolute.departmentgroupquest.domain.type.QuestType;

import java.time.LocalDate;

public record UpdateDepartQuestDetailRequest(
        String departmentGroup,
        String department,
        int year,
        int week,
        int month,
        QuestType questType,
        LocalDate date,
        double revenue,
        double laborCost,
        double designServiceFee,
        double employeeSalary,
        double retirementBenefit,
        double socialInsuranceBenefit
) {
}
