package com.blaybus.appsolute.board.controller;

import com.blaybus.appsolute.board.domain.request.BoardRequest;
import com.blaybus.appsolute.board.domain.response.BoardResponse;
import com.blaybus.appsolute.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<String> saveBoard(@RequestBody BoardRequest boardRequest) {
        boardService.saveBoard(boardRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<BoardResponse>> getBoard() {
        return ResponseEntity.ok(boardService.getBoard());
    }

}
