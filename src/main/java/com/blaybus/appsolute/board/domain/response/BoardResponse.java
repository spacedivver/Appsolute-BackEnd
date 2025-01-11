package com.blaybus.appsolute.board.domain.response;

import com.blaybus.appsolute.board.domain.entity.Board;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class BoardResponse {
    private Long boardId;
    private String title;
    private String content;
    private Date createdAt;

    public static BoardResponse fromEntity(Board board) {
        return BoardResponse.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .createdAt(board.getCreatedAt())
                .build();
    }

}
