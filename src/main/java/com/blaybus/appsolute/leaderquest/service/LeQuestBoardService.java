package com.blaybus.appsolute.leaderquest.service;

import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
import com.blaybus.appsolute.leaderquest.domain.entity.LeQuestBoard;
import com.blaybus.appsolute.leaderquest.domain.entity.LeaderQuest;
import com.blaybus.appsolute.leaderquest.domain.request.LeQuestBoardRequest;
import com.blaybus.appsolute.leaderquest.repository.JpaLeQuestBoardRepository;
import com.blaybus.appsolute.leaderquest.repository.JpaLeaderQuestRepository;
import com.blaybus.appsolute.user.domain.entity.User;
import com.blaybus.appsolute.user.repository.JpaUserRepository;
import com.google.api.services.sheets.v4.Sheets;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LeQuestBoardService {
    private final JpaLeQuestBoardRepository leQuestBoardRepository;
    private final JpaLeaderQuestRepository leaderQuestRepository;
    private final JpaUserRepository userRepository;

    public List<LeQuestBoard> getLeQuestBoard(Long userId, Integer period) {
        return leQuestBoardRepository.findByUserIdAndLeaderQuest_Period(userId, period);
    }

    public void saveLeQuestBoard(LeQuestBoardRequest leQuestBoardRequest) {

        User user = userRepository.findByEmployeeNumber(leQuestBoardRequest.getEmployeeNumber())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당 사용자가 없습니다.", 404, LocalDateTime.now())
                ));

        LeQuestBoard leQuestBoard = LeQuestBoard.builder()
                .userId(user.getId())
                .leaderQuestId(leQuestBoardRequest.getLeaderQuestId())
                .questStatus(leQuestBoardRequest.getQuestStatus())
                .actualPoint(leQuestBoardRequest.getActualPoint())
                .build();

        leQuestBoardRepository.save(leQuestBoard);
    }

    public void updateLeQuestBoardXP(LeQuestBoardRequest leQuestBoardRequest) {
        User user = userRepository.findByEmployeeNumber(leQuestBoardRequest.getEmployeeNumber())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당 사용자가 없습니다.", 404, LocalDateTime.now())
                ));

        LeaderQuest leaderQuest = leaderQuestRepository.findByLeaderQuestNameAndLeaderQuestType(
                        leQuestBoardRequest.getLeaderQuestName(), LeaderQuest.LeaderQuestType.MONTHLY)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당 퀘스트를 찾을 수 없습니다.", 404, LocalDateTime.now())
                ));

        LeQuestBoard leQuestBoard = leQuestBoardRepository.findByUserIdAndLeaderQuestId(user.getId(), leaderQuest.getLeaderQuestId())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("퀘스트 보드를 찾을 수 없습니다.", 404, LocalDateTime.now())
                ));

        leQuestBoard.updateActualPoint(leQuestBoardRequest.getActualPoint());

        leQuestBoardRepository.save(leQuestBoard);

    }

}
