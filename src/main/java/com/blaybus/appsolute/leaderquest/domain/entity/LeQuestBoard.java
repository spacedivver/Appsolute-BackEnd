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

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "leader_quest_id")
    private Long leaderQuestId;

    @Column(name="month")
    private Long month;

    public enum QuestStatus {
        READY, ONGOING, COMPLETED, FAILED
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private QuestStatus questStatus;

    @Column(name = "granted_point")
    private Long grantedPoint;

    @Column(name="note")
    private String note;

    @Column(name="year")
    private int year;

    public void updateGrantedPoint(Long grantedPoint) {

        if (grantedPoint == null || grantedPoint < 0) {
            this.grantedPoint = 0L;
        } else {
            this.grantedPoint = grantedPoint;
        }

        if (this.grantedPoint == 0) {
            this.questStatus = QuestStatus.READY;
        } else if (this.grantedPoint < 50) {
            this.questStatus = QuestStatus.ONGOING;
        } else {
            this.questStatus = QuestStatus.COMPLETED;
        }
    }

    public void updateLeaderQuestId(Long leaderQuestId) {
        this.leaderQuestId = leaderQuestId;
    }

    public void updateQuestStatus(QuestStatus questStatus) {
        this.questStatus = questStatus;
    }

    public void updateNote(String note) {
        this.note = note;
    }
}
