package com.blaybus.appsolute.leaderquest.service;

import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
import com.blaybus.appsolute.departmentgroupquest.domain.type.QuestStatusType;
import com.blaybus.appsolute.fcm.domain.response.ReadFcmTokenResponse;
import com.blaybus.appsolute.fcm.service.FcmTokenService;
import com.blaybus.appsolute.fcm.service.MessageService;
import com.blaybus.appsolute.leaderquest.domain.entity.LeQuestBoard;
import com.blaybus.appsolute.leaderquest.domain.entity.LeaderQuest;
import com.blaybus.appsolute.leaderquest.domain.request.LeQuestBoardRequest;
import com.blaybus.appsolute.leaderquest.domain.response.LeQuestBoardResponse;
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
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LeQuestBoardService {
    private final JpaLeQuestBoardRepository leQuestBoardRepository;
    private final JpaLeaderQuestRepository leaderQuestRepository;
    private final JpaUserRepository userRepository;
    private final FcmTokenService tokenService;
    private final MessageService messageService;

    public List<LeQuestBoardResponse> getLeQuestBoard(Long userId, Long month) {
        List<LeQuestBoard> leQuestBoards = leQuestBoardRepository.findByUserIdAndMonth(userId, month);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당 사용자가 없습니다.", 404, LocalDateTime.now())
                ));

        return leQuestBoards.stream()
                .map(leQuestBoard -> LeQuestBoardResponse.builder()
                        .employeeName(user.getUserName())
                        .month(leQuestBoard.getMonth())
                        .questStatus(leQuestBoard.getQuestStatus())
                        .grantedPoint(leQuestBoard.getGrantedPoint())
                        .note(leQuestBoard.getNote())
                        .year(leQuestBoard.getYear())
                        .build())
                .collect(Collectors.toList());
    }

    public void updateLeQuestBoard(LeQuestBoardRequest leQuestBoardRequest) {
        User user = userRepository.findByEmployeeNumber(leQuestBoardRequest.getEmployeeNumber())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당 사용자가 없습니다.", 404, LocalDateTime.now())
                ));

        LeaderQuest leaderQuest = leaderQuestRepository.findByLeaderQuestName(
                        leQuestBoardRequest.getLeaderQuestName())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당 퀘스트를 찾을 수 없습니다.", 404, LocalDateTime.now())
                ));

        LeQuestBoard leQuestBoard = leQuestBoardRepository.findByUserIdAndLeaderQuestId(user.getId(), leaderQuest.getLeaderQuestId())
                .orElse(null);

        if (leQuestBoard == null) {
            leQuestBoard = LeQuestBoard.builder()
                    .userId(user.getId())
                    .leaderQuestId(leaderQuest.getLeaderQuestId())
                    .questStatus(LeQuestBoard.QuestStatus.READY)
                    .grantedPoint(0L)
                    .month(leQuestBoardRequest.getMonth())
                    .year(LocalDateTime.now().getYear())
                    .note(leQuestBoardRequest.getNote())
                    .build();
        }

        leQuestBoard.updateGrantedPoint(leQuestBoardRequest.getGrantedPoint());
        leQuestBoard.updateLeaderQuestId(leQuestBoardRequest.getLeaderQuestId());
        leQuestBoard.updateQuestStatus(leQuestBoardRequest.getQuestStatus());
        leQuestBoard.updateNote(leQuestBoardRequest.getNote());

        if(Objects.equals(leQuestBoardRequest.getGrantedPoint(), leaderQuest.getMaxPoint())) {
            leQuestBoard=LeQuestBoard.builder()
                    .questStatus(LeQuestBoard.QuestStatus.COMPLETED)
                    .build();
        } else if (Objects.equals(leQuestBoardRequest.getGrantedPoint(), leaderQuest.getMediumPoint())) {
            leQuestBoard=LeQuestBoard.builder()
                    .questStatus(LeQuestBoard.QuestStatus.ONGOING)
                    .build();
        } else {
            leQuestBoard=LeQuestBoard.builder()
                    .questStatus(LeQuestBoard.QuestStatus.READY)
                    .build();
        }

        String title = "경험치 획득!";
        String message=leQuestBoard.getMonth()+"월"+leaderQuest.getLeaderQuestName()
                +"관련 리더부여 퀘스트"+ leQuestBoard.getGrantedPoint() + "경험치를 획득하였습니다.";

        if(!LeQuestBoard.QuestStatus.READY.equals(leQuestBoard.getQuestStatus())) {
            List<ReadFcmTokenResponse> tokens = tokenService.getFcmTokens(user.getId());

            if (!tokens.isEmpty()) {
                ReadFcmTokenResponse token = tokens.get(0);
                messageService.sendMessageTo(user, token.fcmToken(), title, message, null);
            }
        }

        leQuestBoardRepository.save(leQuestBoard);

    }

}
