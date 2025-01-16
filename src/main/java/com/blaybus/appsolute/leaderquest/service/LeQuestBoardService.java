package com.blaybus.appsolute.leaderquest.service;

import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LeQuestBoardService {
    private final JpaLeQuestBoardRepository leQuestBoardRepository;
    private final JpaLeaderQuestRepository leaderQuestRepository;
    private final JpaUserRepository userRepository;
    private final FcmTokenService tokenService;
    private final MessageService messageService;
    private final JpaLeaderQuestRepository jpaLeaderQuestRepository;

    public List<LeQuestBoardResponse> getLeQuestBoard(String userId, Long month) {

        // 사용자 조회
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당 사용자가 없습니다.", 404, LocalDateTime.now())
                ));

        // LeQuestBoard 조회
        Optional<LeQuestBoard> leQuestBoards = leQuestBoardRepository.findByUserIdAndMonth(user.getId(), month);

        // 결과 리스트 생성
        List<LeQuestBoardResponse> responseList = new ArrayList<>();

        if (leQuestBoards.isPresent()) {
            LeQuestBoard leQuestBoard = leQuestBoards.get();
            LeaderQuest leaderQuest = jpaLeaderQuestRepository.findByLeaderQuestId(leQuestBoard.getLeaderQuestId());

            // LeQuestBoardResponse 객체를 생성하고 리스트에 추가
            LeQuestBoardResponse response = LeQuestBoardResponse.builder()
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

            responseList.add(response);
        }

        return responseList;
    }


    public void updateLeQuestBoard(LeQuestBoardRequest leQuestBoardRequest) {
        if (leQuestBoardRequest.getEmployeeNumber() == null || leQuestBoardRequest.getMonth() == null) {
            throw new ApplicationException(
                    ErrorStatus.toErrorStatus("사번과 해당 월이 필요합니다.", 400, LocalDateTime.now())
            );
        }

        // User 조회
        User user = userRepository.findByEmployeeNumber(leQuestBoardRequest.getEmployeeNumber())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당 사용자가 없습니다.", 404, LocalDateTime.now())
                ));

        // LeaderQuest 조회
        LeaderQuest leaderQuest = (leQuestBoardRequest.getLeaderQuestName() != null)
                ? leaderQuestRepository.findByLeaderQuestName(leQuestBoardRequest.getLeaderQuestName()).orElse(null)
                : null;

        // 기존 데이터 조회 또는 새로 생성
        LeQuestBoard leQuestBoard = leQuestBoardRepository.findByUserIdAndMonth(user.getId(), leQuestBoardRequest.getMonth())
                .orElseGet(() -> {
                    LeQuestBoard newLeQuestBoard = LeQuestBoard.builder()
                            .userId(user.getId())
                            .leaderQuestId(0L)
                            .questStatus("")
                            .grantedPoint(0L)
                            .month(leQuestBoardRequest.getMonth())
                            .year(LocalDateTime.now().getYear())
                            .build();
                    return leQuestBoardRepository.save(newLeQuestBoard);
                });

        // 조건부 필드 업데이트
        if (leQuestBoardRequest.getGrantedPoint() != null) {
            leQuestBoard.updateGrantedPoint(leQuestBoardRequest.getGrantedPoint());
        }
        if (leQuestBoardRequest.getNote() != null) {
            leQuestBoard.updateNote(leQuestBoardRequest.getNote());
        }
        if (leaderQuest != null) {
            leQuestBoard.updateLeaderQuestId(leaderQuest.getLeaderQuestId());
            updateQuestStatus(leaderQuest, leQuestBoard, leQuestBoardRequest.getGrantedPoint());
        }

        // 알림 메시지 생성 및 발송
        notifyUser(user, leaderQuest, leQuestBoard);

        // 업데이트된 데이터 저장
        leQuestBoardRepository.save(leQuestBoard);
    }

    private void updateQuestStatus(LeaderQuest leaderQuest, LeQuestBoard leQuestBoard, Long grantedPoint) {
        if (Objects.equals(grantedPoint, leaderQuest.getMaxPoint())) {
            leQuestBoard.updateQuestStatus("Max");
        } else if (Objects.equals(grantedPoint, leaderQuest.getMediumPoint())) {
            leQuestBoard.updateQuestStatus("Med");
        } else {
            leQuestBoard.updateQuestStatus("");
        }
    }

    private void notifyUser(User user, LeaderQuest leaderQuest, LeQuestBoard leQuestBoard) {
        String title = "경험치 획득!";
        String message = (leaderQuest != null)
                ? leQuestBoard.getMonth() + "월 " + leaderQuest.getLeaderQuestName() + "에서 " + leQuestBoard.getGrantedPoint() + " 경험치를 획득하였습니다."
                : leQuestBoard.getMonth() + "월 리더 퀘스트에서 " + leQuestBoard.getGrantedPoint() + " 경험치를 획득하였습니다.";

        if (!Objects.equals(leQuestBoard.getQuestStatus(), "")) {
            List<ReadFcmTokenResponse> tokens = tokenService.getFcmTokens(user.getId());
            tokens.forEach(token -> messageService.sendMessageTo(user, token.fcmToken(), title, message, null));
        }
    }

}
