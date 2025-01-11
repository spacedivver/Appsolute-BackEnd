package com.blaybus.appsolute.departmentgroupquest.domain.request;

import com.blaybus.appsolute.departmentgroupquest.domain.type.QuestType;

public record UpdateDepartmentGroupQuestRequest(
        String department,
        String departmentGroup,
        QuestType questType,
        Double maxThreshold,
        Double mediumThreshold,
        Long maxPoint,
        Long mediumPoint,
        int year,
        int period,
        Long xp,
        String notes,
        Double productivity
) {
}
