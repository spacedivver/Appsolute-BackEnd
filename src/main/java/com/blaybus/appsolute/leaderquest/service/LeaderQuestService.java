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

    public void saveLeaderQuest(LeaderQuestRequest leaderQuestRequest) {
        LeaderQuest leaderQuest = LeaderQuest.builder()
                .leaderQuestName(leaderQuestRequest.getLeader_quest_name())
                .leaderQuestType(leaderQuestRequest.getQuest_type())
                .maxThreshold(leaderQuestRequest.getMax_threshold())
                .mediumThreshold(leaderQuestRequest.getMedium_threshold())
                .mediumPoint(leaderQuestRequest.getMedium_point())
                .maxPoint(leaderQuestRequest.getMax_point())
                .point(leaderQuestRequest.getPoint())
                .notes(leaderQuestRequest.getNotes())
                .rate(leaderQuestRequest.getRate())
                .build();

        jpaLeaderQuestRepository.save(leaderQuest);
    }

    public List<LeaderQuest> getLeaderQuest() throws IOException {
        return jpaLeaderQuestRepository.findAll();
    }


}
