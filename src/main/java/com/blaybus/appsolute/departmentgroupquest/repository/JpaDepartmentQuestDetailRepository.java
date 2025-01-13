package com.blaybus.appsolute.departmentgroupquest.repository;

import com.blaybus.appsolute.departmentgroupquest.domain.entity.DepartmentGroupQuest;
import com.blaybus.appsolute.departmentgroupquest.domain.entity.DepartmentQuestDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaDepartmentQuestDetailRepository extends JpaRepository<DepartmentQuestDetail, Long> {
    List<DepartmentQuestDetail> findByDepartmentGroupQuest(DepartmentGroupQuest departmentGroupQuest);
}
