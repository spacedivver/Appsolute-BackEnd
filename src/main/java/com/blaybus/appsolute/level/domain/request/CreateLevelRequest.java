package com.blaybus.appsolute.level.domain.request;

import com.blaybus.appsolute.level.domain.entity.Level;

public record CreateLevelRequest(
        String previousLevel,
        String levelName,
        Long levelAchievement,
        Long maxPoint
) {
    public Level toEntity() {
        return Level.builder()
                .levelAchievement(levelAchievement)
                .levelName(levelName)
                .maxPoint(maxPoint != null ? maxPoint : Long.MAX_VALUE)
                .build();
    }
}
