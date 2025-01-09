package com.blaybus.appsolute.leaderquest.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="LEADER_QUEST")
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class LeaderQuest {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leader_quest_id")
    private Long leaderQuestId;

    @Column(name = "leader_quest_name", nullable = false)
    private String leaderQuestName;

    public enum LeaderQuestType {
        WEEKLY, MONTHLY
    }

    @Column(name = "leader_quest_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private LeaderQuestType leaderQuestType;

    @Column(name = "max_threshold", nullable = false)
    private String maxThreshold;

    @Column(name = "medium_threshold", nullable = false)
    private String mediumThreshold;

    @Column(name = "medium_point", nullable = false)
    private Long mediumPoint;

    @Column(name = "max_point", nullable = false)
    private Long maxPoint;

    @Column(name = "year", nullable = false)
    private String year;

    @Column(name = "period", nullable = false)
    private Integer period;


    // 테스트용 팩토리 메서드
    public static LeaderQuest createTestEntity(String name, LeaderQuestType type, String max, String medium, Long mediumPoint, Long maxPoint, String year, Integer period) {
        LeaderQuest leaderQuest = new LeaderQuest();
        leaderQuest.leaderQuestName = name;
        leaderQuest.leaderQuestType = type;
        leaderQuest.maxThreshold = max;
        leaderQuest.mediumThreshold = medium;
        leaderQuest.mediumPoint = mediumPoint;
        leaderQuest.maxPoint = maxPoint;
        leaderQuest.year = year;
        leaderQuest.period = period;
        return leaderQuest;
    }

}
