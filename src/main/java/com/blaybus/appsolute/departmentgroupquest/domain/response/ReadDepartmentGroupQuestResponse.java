package com.blaybus.appsolute.departmentgroupquest.domain.response;

import com.blaybus.appsolute.departmentgroupquest.domain.entity.DepartmentGroupQuest;
import com.blaybus.appsolute.departmentgroupquest.domain.type.QuestStatusType;
import com.blaybus.appsolute.departmentgroupquest.domain.type.QuestType;
import lombok.Builder;

@Builder
public record ReadDepartmentGroupQuestResponse(
        Long departmentGroupQuestId,
        QuestType departmentQuestType,
        Double maxThreshold,
        Double mediumThreshold,
        QuestStatusType departmentGroupQuestStatus,
        Long mediumPoint, Long maxPoint,
        Integer year,
        Integer month,
        Integer week,
        Long nowXP,
        String departmentName,
        String departmentGroupName,
        String note
)
{
    public static ReadDepartmentGroupQuestResponse fromEntity(DepartmentGroupQuest departmentGroupQuest) {
        return ReadDepartmentGroupQuestResponse.builder()
                .departmentGroupQuestId(departmentGroupQuest.getDepartmentGroupQuestId())
                .departmentQuestType(departmentGroupQuest.getDepartmentQuestType())
                .maxThreshold(departmentGroupQuest.getMaxThreshold())
                .mediumThreshold(departmentGroupQuest.getMediumThreshold())
                .maxPoint(departmentGroupQuest.getMaxPoint())
                .mediumPoint(departmentGroupQuest.getMediumPoint())
                .nowXP(departmentGroupQuest.getNowXP())
                .departmentName(departmentGroupQuest.getDepartmentGroup().getDepartmentGroupName())
                .departmentGroupName(departmentGroupQuest.getDepartmentGroup().getDepartmentGroupName())
                .note(departmentGroupQuest.getNote())
                .build();
    }
}
