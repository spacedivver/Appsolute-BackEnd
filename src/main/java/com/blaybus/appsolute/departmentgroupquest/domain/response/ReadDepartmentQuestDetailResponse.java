package com.blaybus.appsolute.departmentgroupquest.domain.response;

import com.blaybus.appsolute.departmentgroupquest.domain.entity.DepartmentGroupQuest;
import com.blaybus.appsolute.departmentgroupquest.domain.entity.DepartmentQuestDetail;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ReadDepartmentQuestDetailResponse(
        LocalDate departmentQuestDetailDate,
        Double revenue,
        Double laborCost,
        Double designServiceFee,
        Double employeeSalary,
        Double retirementBenefit,
        Double socialInsuranceBenefit
) {
    public static ReadDepartmentQuestDetailResponse fromEntity(DepartmentQuestDetail departmentQuestDetail) {
        return ReadDepartmentQuestDetailResponse.builder()
                .departmentQuestDetailDate(departmentQuestDetail.getDepartmentQuestDetailDate())
                .revenue(departmentQuestDetail.getRevenue())
                .laborCost(departmentQuestDetail.getLaborCost())
                .designServiceFee(departmentQuestDetail.getDesignServiceFee())
                .employeeSalary(departmentQuestDetail.getEmployeeSalary())
                .retirementBenefit(departmentQuestDetail.getRetirementBenefit())
                .socialInsuranceBenefit(departmentQuestDetail.getSocialInsuranceBenefit())
                .build();
    }
}
