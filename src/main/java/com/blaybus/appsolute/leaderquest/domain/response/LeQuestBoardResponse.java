package com.blaybus.appsolute.leaderquest.domain.response;

import com.blaybus.appsolute.leaderquest.domain.entity.LeQuestBoard;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LeQuestBoardResponse {

    private String employeeName;
    private Long month;
    private String questStatus;
    private Long grantedPoint;
    private String note;
    private int year;
    private String maxThreshold;
    private String mediumThreshold;
    private String questName;

}
