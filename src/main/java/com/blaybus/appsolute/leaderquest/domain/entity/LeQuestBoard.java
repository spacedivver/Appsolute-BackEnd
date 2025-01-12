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

    private Long month;

    public void updateActualPoint(Double actualPoint) {
        if (actualPoint == null || actualPoint < 0) {
            throw new IllegalArgumentException("유효하지 않은 경험치 값입니다.");
        }
        this.actualPoint = actualPoint;

        if (actualPoint == 0) {
            this.questStatus = QuestStatus.READY;
        } else if (actualPoint < 50) {
            this.questStatus = QuestStatus.ONGOING;
        } else if (actualPoint >= 50) {
            this.questStatus = QuestStatus.COMPLETED;
        }
    }
}
