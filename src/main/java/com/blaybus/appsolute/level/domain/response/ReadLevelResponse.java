package com.blaybus.appsolute.level.domain.response;

import com.blaybus.appsolute.level.domain.entity.Level;
import lombok.Builder;

@Builder
public record ReadLevelResponse(
        String levelName,
        Long requiredPoint
) {
    public static ReadLevelResponse fromEntity(Level level) {
        return ReadLevelResponse.builder()
                .levelName(level.getLevelName())
                .requiredPoint(level.getLevelAchievement())
                .build();
    }
}
