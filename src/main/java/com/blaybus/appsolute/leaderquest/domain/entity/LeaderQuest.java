package com.blaybus.appsolute.leaderquest.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="LEADER_QUEST")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LeaderQuest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leader_quest_id")
    private Long leaderQuestId;

    @Column(name = "leader_quest_name")
    private String leaderQuestName;

    public enum LeaderQuestType {
        WEEKLY, MONTHLY
    }

    @Column(name = "quest_type")
    @Enumerated(EnumType.STRING)
    private LeaderQuestType leaderQuestType;

    @Column(name="rate")
    private String rate;

    @Column(name="total_quest_point")
    private Long totalQuestPoint;

    @Column(name = "max_point")
    private Long maxPoint;

    @Column(name = "medium_point")
    private Long mediumPoint;

    @Column(name = "max_threshold")
    private String maxThreshold;

    @Column(name = "medium_threshold")
    private String mediumThreshold;

    @Column(name="note")
    private String note;


}
