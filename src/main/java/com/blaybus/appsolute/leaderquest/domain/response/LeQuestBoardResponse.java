package com.blaybus.appsolute.leaderquest.domain.response;

import com.blaybus.appsolute.board.domain.entity.Board;
import com.blaybus.appsolute.board.domain.response.BoardResponse;
import com.blaybus.appsolute.leaderquest.domain.entity.LeQuestBoard;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class LeQuestBoardResponse {

    private String employeeName;
    private Long month;
    private LeQuestBoard.QuestStatus questStatus;
    private Long grantedPoint;
    private String note;
    private int year;

}
