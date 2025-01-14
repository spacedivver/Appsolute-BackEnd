package com.blaybus.appsolute.departmentgroupquest.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "DEPARTMENT_QUEST_DETAIL")
public class DepartmentQuestDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_quest_detail_id")
    private Long departmentQuestDetailId;

    @Column(name = "department_quest_detail_date")
    private LocalDate departmentQuestDetailDate;

    @Column(name = "revenue")
    private Double revenue;

    @Column(name = "labor_cost")
    private Double laborCost;

    @Column(name = "design_service_fee")
    private Double designServiceFee;

    @Column(name = "employee_salary")
    private Double employeeSalary;

    @Column(name = "retirement_benefit")
    private Double retirementBenefit;

    @Column(name = "social_insurance_benefit")
    private Double socialInsuranceBenefit;

    @ManyToOne
    @JoinColumn(name = "department_quest_id")
    private DepartmentGroupQuest departmentGroupQuest;

    public void updateAll(LocalDate departmentQuestDetailDate, Double revenue, Double laborCost, Double designServiceFee, Double employeeSalary, Double retirementBenefit, Double socialInsuranceBenefit) {
        this.departmentQuestDetailDate = departmentQuestDetailDate;
        this.revenue = revenue;
        this.laborCost = laborCost;
        this.designServiceFee = designServiceFee;
        this.employeeSalary = employeeSalary;
        this.retirementBenefit = retirementBenefit;
        this.socialInsuranceBenefit = socialInsuranceBenefit;
    }

    @Builder
    public DepartmentQuestDetail(Long departmentQuestDetailId, LocalDate departmentQuestDetailDate, Double revenue, Double laborCost, Double designServiceFee, Double employeeSalary, Double retirementBenefit, Double socialInsuranceBenefit, DepartmentGroupQuest departmentGroupQuest) {
        this.departmentQuestDetailId = departmentQuestDetailId;
        this.departmentQuestDetailDate = departmentQuestDetailDate;
        this.revenue = revenue;
        this.laborCost = laborCost;
        this.designServiceFee = designServiceFee;
        this.employeeSalary = employeeSalary;
        this.retirementBenefit = retirementBenefit;
        this.socialInsuranceBenefit = socialInsuranceBenefit;
        this.departmentGroupQuest = departmentGroupQuest;
    }
}
