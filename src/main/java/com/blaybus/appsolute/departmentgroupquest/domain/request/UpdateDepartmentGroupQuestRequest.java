package com.blaybus.appsolute.departmentgroupquest.domain.request;

import com.blaybus.appsolute.departmentgroupquest.domain.type.QuestType;

public record UpdateDepartmentGroupQuestRequest(
        String department,
        String departmentGroup,
        QuestType questType,
        int year,
        int period,
        Long xp
) {
}
