package com.blaybus.appsolute.departmentgroupquest.repository;

import com.blaybus.appsolute.departmentgroup.domain.DepartmentGroup;
import com.blaybus.appsolute.departmentgroupquest.domain.entity.DepartmentGroupQuest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaDepartmentGroupQuestRepository extends JpaRepository<DepartmentGroupQuest, Long> {
    Optional<DepartmentGroupQuest> findByDepartmentGroupAndYearAndMonth(DepartmentGroup departmentGroup, Integer year, Integer month);
    Optional<DepartmentGroupQuest> findByDepartmentGroupAndYearAndWeek(DepartmentGroup departmentGroup, Integer year, Integer week);
    List<DepartmentGroupQuest> findByDepartmentGroupAndYear(DepartmentGroup departmentGroup, Integer year);
    void deleteByDepartmentGroupAndYear(DepartmentGroup departmentGroup, Integer year);
    List<DepartmentGroupQuest> findByDepartmentGroup(DepartmentGroup departmentGroup);
}
