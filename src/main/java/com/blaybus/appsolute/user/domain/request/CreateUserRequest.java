package com.blaybus.appsolute.user.domain.request;

import java.util.List;

public record CreateUserRequest(
        String employeeNumber,
        String employeeName,
        String joiningDate,
        String departmentName,
        String departmentGroupName,
        String level,
        String userId,
        String initialPassword
) {
}
