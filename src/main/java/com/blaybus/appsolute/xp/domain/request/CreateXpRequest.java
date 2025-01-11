package com.blaybus.appsolute.xp.domain.request;

import java.util.List;

public record CreateXpRequest(
        String employeeNumber,
        String xp,
        int year
) {
}
