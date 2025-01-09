package com.blaybus.appsolute.leaderquest.service;

import com.blaybus.appsolute.leaderquest.domain.entity.LeQuestBoard;
import com.blaybus.appsolute.leaderquest.domain.entity.LeaderQuest;
import com.blaybus.appsolute.leaderquest.repository.JpaLeQuestBoardRepository;
import com.blaybus.appsolute.leaderquest.repository.JpaLeaderQuestRepository;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeQuestBoardService {
    private final JpaLeQuestBoardRepository repository;
    private final JpaLeaderQuestRepository leaderQuestRepository;
    private final Sheets googleSheet;

    @Value("${google.spreadsheet.id}")
    private String spreadsheetId;

    public List<LeQuestBoard> getLeQuestBoards(Long userId, Integer period) {
        return repository.findByUserIdAndLeaderQuest_Period(userId, period);
    }

    public void importLeQuestBoards(String range) throws IOException {
        ValueRange response = googleSheet.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();

        List<List<Object>> values = response.getValues();
        if (values != null && !values.isEmpty()) {
            for (int i = 1; i < values.size(); i++) { // 첫 행(헤더)은 제외
                List<Object> row = values.get(i);

                Long userId = Long.parseLong(row.get(1).toString());
                String leaderQuestName = row.get(3).toString();
                String questStatus = row.get(4).toString();
                Double actualPoint = Double.parseDouble(row.get(5).toString());

                // 리더 퀘스트 검색
                LeaderQuest leaderQuest = leaderQuestRepository.findByName(leaderQuestName)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Quest Name: " + leaderQuestName));

                // LeQuestBoard 엔티티 생성 및 저장
                LeQuestBoard leQuestBoard = LeQuestBoard.builder()
                        .userId(userId)
                        .leaderQuest(leaderQuest)
                        .questStatus(LeQuestBoard.QuestStatus.valueOf(questStatus.toUpperCase()))
                        .actualPoint(actualPoint)
                        .build();

                repository.save(leQuestBoard);

            }
        }
    }
}
