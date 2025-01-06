package com.blaybus.appsolute.departmentgroup.domain;

import com.blaybus.appsolute.department.domain.entity.Department;
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

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Builder
    public DepartmentGroup(Long departmentGroupId, String departmentGroupName, Department department) {
        this.departmentGroupId = departmentGroupId;
        this.departmentGroupName = departmentGroupName;
        this.department = department;
    }
}
