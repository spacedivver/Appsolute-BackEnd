package com.blaybus.appsolute.leaderquest.domain.request;

import com.blaybus.appsolute.leaderquest.domain.entity.LeQuestBoard;
import lombok.Data;

@Data
public class LeQuestBoardRequest {

    private Long userId;
    private Long leaderQuestId;
    private Double actualPoint;
    public LeQuestBoard.QuestStatus questStatus;
    private String employeeNumber;

}
