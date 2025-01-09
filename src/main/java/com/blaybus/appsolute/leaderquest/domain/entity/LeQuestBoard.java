package com.blaybus.appsolute.leaderquest.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "LE_QUEST_BOARD")
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LeQuestBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "le_board_id")
    private Long leBoardId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_quest_id", nullable = false)
    private LeaderQuest leaderQuest;

    @Column(name = "quest_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestStatus questStatus;

    @Column(name = "actual_point", nullable = false)
    private Double actualPoint;

    public enum QuestStatus {
        READY, ONGOING, COMPLETED, FAILED
    }

    // 테스트용 팩토리 메서드
    public static LeQuestBoard createTestEntity(Long userId, LeaderQuest leaderQuest, QuestStatus questStatus, Double actualPoint) {
        LeQuestBoard leQuestBoard = new LeQuestBoard();
        leQuestBoard.userId = userId;
        leQuestBoard.leaderQuest = leaderQuest;
        leQuestBoard.questStatus = questStatus;
        leQuestBoard.actualPoint = actualPoint;
        return leQuestBoard;
    }
}
