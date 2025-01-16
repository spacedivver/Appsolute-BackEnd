package com.blaybus.appsolute.level.domain.request;

import com.blaybus.appsolute.level.domain.entity.Level;

public record CreateLevelRequest(
        String previousLevel,
        String levelName,
        String nextLevel,
        Long levelAchievement
) {
    public Level toEntity(Long maxPoint) {
        return Level.builder()
                .levelAchievement(levelAchievement)
                .levelName(levelName)
                .maxPoint(maxPoint)
                .build();
    }
}
