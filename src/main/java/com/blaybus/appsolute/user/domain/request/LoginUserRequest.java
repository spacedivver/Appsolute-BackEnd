package com.blaybus.appsolute.user.domain.request;

public record LoginUserRequest(
        String userId,
        String password
) {
}
