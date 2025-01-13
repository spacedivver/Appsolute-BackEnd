package com.blaybus.appsolute.departmentgroupquest.domain.response;

import com.blaybus.appsolute.departmentgroupquest.domain.entity.DepartmentGroupQuest;
import com.blaybus.appsolute.departmentgroupquest.domain.type.QuestStatusType;
import lombok.Builder;

import java.util.List;

@Builder
public record ReadDepartQuestDetailResponse(
        List<ReadDepartmentQuestDetailResponse> detailList,
        Long gainedXP,
        Double productivity,
        QuestStatusType questStatus
) {
    public static ReadDepartQuestDetailResponse from(List<ReadDepartmentQuestDetailResponse> detailList, Long gainedXP, DepartmentGroupQuest departmentGroupQuest) {
        return ReadDepartQuestDetailResponse.builder()
                .detailList(detailList)
                .gainedXP(gainedXP)
                .productivity(departmentGroupQuest.getProductivity())
                .questStatus(departmentGroupQuest.getDepartmentGroupQuestStatus())
                .build();
    }
}
