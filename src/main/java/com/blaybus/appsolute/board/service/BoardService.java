package com.blaybus.appsolute.board.service;

import com.blaybus.appsolute.board.domain.entity.Board;
import com.blaybus.appsolute.board.domain.request.BoardRequest;
import com.blaybus.appsolute.board.domain.response.BoardResponse;
import com.blaybus.appsolute.board.repository.JpaBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private JpaBoardRepository jpaBoardRepository;

    public void saveBoard(BoardRequest boardRequest) {
        Board board=Board.builder()
                .title(boardRequest.getTitle())
                .content(boardRequest.getContent())
                .build();
        jpaBoardRepository.save(board);
    }

    public List<BoardResponse> getBoard() {
        List<Board> boards = jpaBoardRepository.findAll();

        return boards.stream()
                .map(BoardResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
