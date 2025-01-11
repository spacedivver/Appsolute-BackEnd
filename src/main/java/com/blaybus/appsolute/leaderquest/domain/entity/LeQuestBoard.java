package com.blaybus.appsolute.leaderquest.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "LE_QUEST_BOARD")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LeQuestBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "le_board_id")
    private Long leBoardId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "quest_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestStatus questStatus;

    @Column(name = "actual_point", nullable = false)
    private Double actualPoint;

    @Column(name = "leader_quest_id", nullable = false)
    private Long leaderQuestId;

    public enum QuestStatus {
        READY, ONGOING, COMPLETED, FAILED
    }
}
