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
        Long lastYearXP,
        Long thisYearXP,
        Long nextLevelRemainXP
) {
    public static ReadUserResponse from(User user, Long lastYearXP, Long thisYearXP, Long nextLevelRemainXP) {
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
                .lastYearXP(lastYearXP)
                .thisYearXP(thisYearXP)
                .nextLevelRemainXP(nextLevelRemainXP)
                .build();
    }
}
