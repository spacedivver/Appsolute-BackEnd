package com.blaybus.appsolute.xp.domain.request;

public record UpdateXpRequest(
        String employeeNumber,
        int year,
        String xp
) {
}
