package com.blaybus.appsolute.board.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name="Board")
public class Board {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long boardId;

    @Column(name="title")
    private String title;

    @Column(name="content",columnDefinition="TEXT")
    private String content;

    @Column(name = "created_at")
    private Date createdAt;

    @Builder
    public Board(String title, String content, Date createdAt) {
        this.title = title;
        this.content = content;
    }

}
