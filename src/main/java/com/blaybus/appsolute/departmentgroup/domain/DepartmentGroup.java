package com.blaybus.appsolute.departmentgroup.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DEPARTMENT_GROUP")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepartmentGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentGroupId;

    @Column(name = "department_group_name", columnDefinition = "VARCHAR(20)")
    private String departmentGroupName;

    @Column(name = "department_name")
    private String departmentName;

    @Builder
    public DepartmentGroup(Long departmentGroupId, String departmentGroupName, String departmentName) {
        this.departmentGroupId = departmentGroupId;
        this.departmentGroupName = departmentGroupName;
        this.departmentName = departmentName;
    }
}
