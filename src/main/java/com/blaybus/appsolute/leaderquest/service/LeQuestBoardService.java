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
import java.util.Optional;
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

    public List<LeQuestBoardResponse> getLeQuestBoard(String userId, Long month) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당 사용자가 없습니다.", 404, LocalDateTime.now())
                ));

        List<LeQuestBoard> leQuestBoards = leQuestBoardRepository.findByUserIdAndMonth(user.getId(), month);

        return leQuestBoards.stream()
                .map(leQuestBoard -> {
                    LeaderQuest leaderQuest = leQuestBoard.getLeaderQuest();
                    return LeQuestBoardResponse.builder()
                            .employeeName(user.getUserName())
                            .month(leQuestBoard.getMonth())
                            .questStatus(leQuestBoard.getQuestStatus())
                            .grantedPoint(leQuestBoard.getGrantedPoint())
                            .note(leQuestBoard.getNote())
                            .year(leQuestBoard.getYear())
                            .questName(leaderQuest.getLeaderQuestName())
                            .maxThreshold(leaderQuest.getMaxThreshold())
                            .mediumThreshold(leaderQuest.getMediumThreshold())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public void updateLeQuestBoard(LeQuestBoardRequest leQuestBoardRequest) {
        // User 조회
        User user = userRepository.findByEmployeeNumber(leQuestBoardRequest.getEmployeeNumber())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당 사용자가 없습니다.", 404, LocalDateTime.now())
                ));

        // LeaderQuest 조회 (null 허용)
        LeaderQuest leaderQuest = leaderQuestRepository.findByLeaderQuestName(leQuestBoardRequest.getLeaderQuestName())
                .orElse(null);

        // 기존 데이터 조회
        List<LeQuestBoard> existingBoards = leQuestBoardRepository.findByUserIdAndMonth(user.getId(), leQuestBoardRequest.getMonth());

        LeQuestBoard leQuestBoard;
        if (!existingBoards.isEmpty()) {
            // 기존 데이터가 있으면 첫 번째 요소를 사용
            leQuestBoard = existingBoards.get(0);
        } else {
            // 기존 데이터가 없으면 새로 생성
            leQuestBoard = LeQuestBoard.builder()
                    .userId(user.getId())
                    .leaderQuestId(leaderQuest != null ? leaderQuest.getLeaderQuestId() : null)
                    .questStatus(LeQuestBoard.QuestStatus.READY)
                    .grantedPoint(0L)
                    .month(leQuestBoardRequest.getMonth())
                    .year(LocalDateTime.now().getYear())
                    .note(leQuestBoardRequest.getNote())
                    .build();
            leQuestBoardRepository.save(leQuestBoard);
        }

        // 전달받은 값으로 데이터 업데이트
        leQuestBoard.updateGrantedPoint(leQuestBoardRequest.getGrantedPoint());
        if (leaderQuest != null) {
            leQuestBoard.updateLeaderQuestId(leaderQuest.getLeaderQuestId());
        }
        leQuestBoard.updateNote(leQuestBoardRequest.getNote());

        // LeaderQuest가 존재하는 경우만 상태 변경
        if (leaderQuest != null) {
            if (Objects.equals(leQuestBoardRequest.getGrantedPoint(), leaderQuest.getMaxPoint())) {
                leQuestBoard.updateQuestStatus(LeQuestBoard.QuestStatus.COMPLETED);
            } else if (Objects.equals(leQuestBoardRequest.getGrantedPoint(), leaderQuest.getMediumPoint())) {
                leQuestBoard.updateQuestStatus(LeQuestBoard.QuestStatus.ONGOING);
            } else {
                leQuestBoard.updateQuestStatus(LeQuestBoard.QuestStatus.READY);
            }
        }

        String title = "경험치 획득!";
        String message = leaderQuest != null ? leQuestBoard.getMonth() + "월 " + leaderQuest.getLeaderQuestName()
                + " 관련 리더 퀘스트에서 " + leQuestBoard.getGrantedPoint() + " 경험치를 획득하였습니다."
                : leQuestBoard.getMonth() + "월 리더 퀘스트에서 " + leQuestBoard.getGrantedPoint() + " 경험치를 획득하였습니다.";

        if (!LeQuestBoard.QuestStatus.READY.equals(leQuestBoard.getQuestStatus())) {
            List<ReadFcmTokenResponse> tokens = tokenService.getFcmTokens(user.getId());
            if (!tokens.isEmpty()) {
                ReadFcmTokenResponse token = tokens.get(0);
                messageService.sendMessageTo(user, token.fcmToken(), title, message, null);
            }
        }

        leQuestBoardRepository.save(leQuestBoard);
    }



}
