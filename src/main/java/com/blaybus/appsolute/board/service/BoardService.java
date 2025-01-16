package com.blaybus.appsolute.board.service;

import com.blaybus.appsolute.board.domain.entity.Board;
import com.blaybus.appsolute.board.domain.request.BoardRequest;
import com.blaybus.appsolute.board.domain.response.BoardResponse;
import com.blaybus.appsolute.board.repository.JpaBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final JpaBoardRepository jpaBoardRepository;

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
                .sorted(Comparator.comparing(Board::getCreatedAt).reversed()) // created_at 기준 내림차순 정렬
                .map(BoardResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
