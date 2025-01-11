package com.blaybus.appsolute.leaderquest.service;

import com.blaybus.appsolute.leaderquest.domain.entity.LeQuestBoard;
import com.blaybus.appsolute.leaderquest.domain.entity.LeaderQuest;
import com.blaybus.appsolute.leaderquest.domain.request.LeQuestBoardRequest;
import com.blaybus.appsolute.leaderquest.domain.request.LeaderQuestRequest;
import com.blaybus.appsolute.leaderquest.repository.JpaLeaderQuestRepository;
import com.blaybus.appsolute.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LeaderQuestService {

    private JpaLeaderQuestRepository jpaLeaderQuestRepository;

    public void saveLeaderQuest(LeaderQuestRequest leaderQuestRequest) throws IOException {
        LeaderQuest leaderQuest=new LeaderQuest();
        leaderQuest.setLeaderQuestName(leaderQuestRequest.getLeader_quest_name());
        leaderQuest.setLeaderQuestType(leaderQuestRequest.getQuest_type());
        leaderQuest.setMaxThreshold(leaderQuestRequest.getMax_threshold());
        leaderQuest.setMediumThreshold(leaderQuestRequest.getMedium_threshold());
        leaderQuest.setMaxPoint(leaderQuestRequest.getMax_point());
        leaderQuest.setMediumPoint(leaderQuestRequest.getMedium_point());
        leaderQuest.setPoint(leaderQuestRequest.getPoint());
        leaderQuest.setRate(leaderQuest.getRate());

        jpaLeaderQuestRepository.save(leaderQuest);

    }

    public List<LeaderQuest> getLeaderQuest() throws IOException {
        return jpaLeaderQuestRepository.findAll();
    }


}
