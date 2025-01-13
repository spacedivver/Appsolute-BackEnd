package com.blaybus.appsolute.board.controller;

import com.blaybus.appsolute.board.domain.request.BoardRequest;
import com.blaybus.appsolute.board.domain.response.BoardResponse;
import com.blaybus.appsolute.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Tag(name="게시판 api")
public class BoardController {
    private final BoardService boardService;

    @Operation(summary="게시판 저장")
    @PostMapping("/save")
    public ResponseEntity<String> saveBoard(@RequestBody BoardRequest boardRequest) {

        boardService.saveBoard(boardRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "게시판 조회")
    @GetMapping
    public ResponseEntity<List<BoardResponse>> getBoard() {

        return ResponseEntity.ok(boardService.getBoard());
    }

}
