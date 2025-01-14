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

    private final JpaLeaderQuestRepository jpaLeaderQuestRepository;

    public void saveLeaderQuest(LeaderQuestRequest leaderQuestRequest) {
        LeaderQuest leaderQuest = LeaderQuest.builder()
                .leaderQuestName(leaderQuestRequest.getLeaderQuestName())
                .leaderQuestType(leaderQuestRequest.getQuestType())
                .maxThreshold(leaderQuestRequest.getMaxThreshold())
                .mediumThreshold(leaderQuestRequest.getMediumThreshold())
                .mediumPoint(leaderQuestRequest.getMediumPoint())
                .maxPoint(leaderQuestRequest.getMaxPoint())
                .totalQuestPoint(leaderQuestRequest.getTotalQuestPoint())
                .note(leaderQuestRequest.getNote())
                .rate(leaderQuestRequest.getRate())
                .build();

        jpaLeaderQuestRepository.save(leaderQuest);
    }

    public List<LeaderQuest> getLeaderQuest() {
        return jpaLeaderQuestRepository.findAll();
    }


}
