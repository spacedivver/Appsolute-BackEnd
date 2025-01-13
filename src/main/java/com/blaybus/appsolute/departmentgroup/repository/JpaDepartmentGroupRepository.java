package com.blaybus.appsolute.departmentgroup.repository;

import com.blaybus.appsolute.departmentgroup.domain.DepartmentGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaDepartmentGroupRepository extends JpaRepository<DepartmentGroup, Long> {
    Optional<DepartmentGroup> findByDepartmentNameAndDepartmentGroupName(String departmentName, String departmentGroupName);
}
