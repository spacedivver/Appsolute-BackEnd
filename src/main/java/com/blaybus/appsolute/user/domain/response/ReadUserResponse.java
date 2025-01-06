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
        Long thisYearXP
) {
    public static ReadUserResponse from(User user, Long lastYearXP, Long thisYearXP) {
        return ReadUserResponse.builder()
                .employeeNumber(user.getEmployeeNumber())
                .userName(user.getUserName())
                .joiningDate(user.getJoiningDate())
                .userId(user.getUserId())
                .departmentName(user.getDepartmentGroup().getDepartment().getDepartmentName())
                .departmentGroupName(user.getDepartmentGroup().getDepartmentGroupName())
                .characterName(user.getCharacters().getCharacterName())
                .characterImage(user.getCharacters().getCharacterImage())
                .lastYearXP(lastYearXP)
                .thisYearXP(thisYearXP)
                .build();
    }
}
