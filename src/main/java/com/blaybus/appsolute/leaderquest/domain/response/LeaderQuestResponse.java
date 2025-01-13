package com.blaybus.appsolute.leaderquest.domain.response;

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
    private String questStatus;
    private String actualPoint;
    private long mediumPoint;
    private long maxPoint;
    private String year;
}