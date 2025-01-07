package com.blaybus.appsolute.level.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "LEVEL")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_id")
    private Long levelId;

    @Column(name = "level_name")
    private String levelName;

    @Column(name = "level_achievement")
    private Long levelAchievement;

    @Column(name = "max_point")
    private Long maxPoint;

    @Builder
    public Level(Long levelId, String levelName, Long levelAchievement, Long maxPoint) {
        this.levelId = levelId;
        this.levelName = levelName;
        this.levelAchievement = levelAchievement;
        this.maxPoint = maxPoint;
    }
}
