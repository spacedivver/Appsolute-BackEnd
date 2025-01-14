package com.blaybus.appsolute.departmentgroupquest.domain.request;

import com.blaybus.appsolute.departmentgroupquest.domain.type.QuestType;

public record UpdateDepartQuestDetailRequest(
        String departmentGroupName,
        String departmentName,
        int year,
        int week,
        int month,
        QuestType questType,
        String date,
        double revenue,
        double laborCost,
        double designServiceFee,
        double employeeSalary,
        double retirementBenefit,
        double socialInsuranceBenefit
) {
}
