package com.blaybus.appsolute.leaderquest.domain.request;

import com.blaybus.appsolute.leaderquest.domain.entity.LeaderQuest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LeaderQuestRequest {

    private String leaderQuestName;
    private LeaderQuest.LeaderQuestType questType;
    private String rate;
    private Long totalQuestPoint;
    private Long maxPoint;
    private Long mediumPoint;
    private String maxThreshold;
    private String mediumThreshold;
    private String note;


}
