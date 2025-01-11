package com.blaybus.appsolute.leaderquest.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="LEADER_QUEST")
@Getter
@Setter
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

    @Column(name = "quest_type", nullable = false)
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

    @Column(name="point",nullable = false)
    private Long point;

    @Column(name="notes", nullable = false)
    private String notes;

    @Column(name="rate", nullable = false)
    private Long rate;

}
