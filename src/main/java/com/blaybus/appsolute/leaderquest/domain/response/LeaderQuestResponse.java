package com.blaybus.appsolute.leaderquest.domain.response;

import com.blaybus.appsolute.leaderquest.domain.entity.LeQuestBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LeaderQuestResponse {
    private Long leaderQuestId;
    private String leaderQuestName;
    private String leaderQuestType;
    private String maxThreshold;
    private String mediumThreshold;
    private LeQuestBoard.QuestStatus questStatus;
    private String grantedPoint;
    private long mediumPoint;
    private long maxPoint;
}