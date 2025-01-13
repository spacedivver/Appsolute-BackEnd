package com.blaybus.appsolute.user.domain.response;

import com.blaybus.appsolute.user.domain.entity.User;
import lombok.Builder;

import java.util.Date;

@Builder
public record ReadUserResponse(
        String employeeNumber,
        String userName,
        Date joiningDate,
        String userId,
        String departmentName,
        String departmentGroupName,
        String characterName,
        String characterImage,
        String levelName,
        Long lastYearTotalXP,
        Long thisYearTotalXP,
        Long nextLevelRemainXP,
        Long thisEvaluationXP,
        Long thisDepartmentGroupXP,
        Long thisProjectXP,
        boolean isLastLevel,
        Long totalXP
) {
    public static ReadUserResponse from(User user, Long lastYearXP, Long thisYearXP, Long nextLevelRemainXP, Long thisEvaluationXP, Long thisDepartmentGroupXP, Long thisProjectXP, boolean isLastLevel, Long totalXP) {
        return ReadUserResponse.builder()
                .employeeNumber(user.getEmployeeNumber())
                .userName(user.getUserName())
                .joiningDate(user.getJoiningDate())
                .userId(user.getUserId())
                .departmentName(user.getDepartmentGroup().getDepartmentName())
                .departmentGroupName(user.getDepartmentGroup().getDepartmentGroupName())
                .characterName(user.getCharacters() != null ? user.getCharacters().getCharacterName() : null)
                .characterImage(user.getCharacters() != null ? user.getCharacters().getCharacterImage() : null)
                .levelName(user.getLevel().getLevelName())
                .lastYearTotalXP(lastYearXP)
                .thisYearTotalXP(thisYearXP)
                .nextLevelRemainXP(nextLevelRemainXP)
                .thisEvaluationXP(thisEvaluationXP)
                .thisDepartmentGroupXP(thisDepartmentGroupXP)
                .thisProjectXP(thisProjectXP)
                .isLastLevel(isLastLevel)
                .totalXP(totalXP)
                .build();
    }
}
