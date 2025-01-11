package com.blaybus.appsolute.leaderquest.domain.request;

import com.blaybus.appsolute.leaderquest.domain.entity.LeaderQuest;
import lombok.Data;

@Data
public class LeaderQuestRequest {

    private String leader_quest_name;
    private LeaderQuest.LeaderQuestType quest_type;
    private String max_threshold;
    private String medium_threshold;
    private Long max_point;
    private Long medium_point;
    private Long point;
    private String notes;
    private String rate;


}
