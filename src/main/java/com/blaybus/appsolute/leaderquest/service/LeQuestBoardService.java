package com.blaybus.appsolute.leaderquest.service;

import com.blaybus.appsolute.leaderquest.domain.entity.LeQuestBoard;
import com.blaybus.appsolute.leaderquest.domain.request.LeQuestBoardRequest;
import com.blaybus.appsolute.leaderquest.repository.JpaLeQuestBoardRepository;
import com.blaybus.appsolute.user.domain.entity.User;
import com.blaybus.appsolute.user.repository.JpaUserRepository;
import com.google.api.services.sheets.v4.Sheets;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LeQuestBoardService {
    private final JpaLeQuestBoardRepository repository;
    private final JpaUserRepository userRepository;
    private final Sheets googleSheet;

    @Value("${google.spreadsheet.id}")
    private String spreadsheetId;

    public List<LeQuestBoard> getLeQuestBoard(Long userId, Integer period) {
        return repository.findByUserIdAndLeaderQuest_Period(userId, period);
    }

    public void saveLeQuestBoard(LeQuestBoardRequest leQuestBoardRequest) {

        User user = userRepository.findById(leQuestBoardRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자 ID가 존재하지 않습니다. " + leQuestBoardRequest.getUserId()));

        LeQuestBoard leQuestBoard = LeQuestBoard.builder()
                .userId(user.getId())
                .leaderQuestId(leQuestBoardRequest.getLeaderQuestId())
                .questStatus(leQuestBoardRequest.getQuestStatus())
                .actualPoint(leQuestBoardRequest.getActualPoint())
                .build();

        repository.save(leQuestBoard);
    }

}
