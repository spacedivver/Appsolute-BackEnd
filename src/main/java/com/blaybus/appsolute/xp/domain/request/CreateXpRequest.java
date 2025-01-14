package com.blaybus.appsolute.xp.domain.request;

public record CreateXpRequest(
        String employeeNumber,
        String xp,
        int year
) {
}
