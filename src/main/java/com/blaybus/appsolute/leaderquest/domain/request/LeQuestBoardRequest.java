package com.blaybus.appsolute.leaderquest.domain.request;

import com.blaybus.appsolute.leaderquest.domain.entity.LeQuestBoard;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LeQuestBoardRequest {

    private Long month;
    private String employeeNumber;
    private String leaderQuestName;
    private Long grantedPoint;
    private String note;

}
