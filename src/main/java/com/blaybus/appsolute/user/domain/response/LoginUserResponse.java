package com.blaybus.appsolute.user.domain.response;

import lombok.Builder;

@Builder
public record LoginUserResponse(
        String jwtToken
) {
}
