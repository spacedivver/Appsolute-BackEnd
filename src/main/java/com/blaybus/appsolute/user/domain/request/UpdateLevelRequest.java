package com.blaybus.appsolute.user.domain.request;

public record UpdateLevelRequest(
        String employeeNumber,
        String levelName
) {
}
