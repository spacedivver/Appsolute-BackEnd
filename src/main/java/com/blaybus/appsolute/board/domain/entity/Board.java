package com.blaybus.appsolute.board.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
@RequiredArgsConstructor
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long boardId;

    private String title;

    @Column(columnDefinition="TEXT")
    private String content;

    private Date createdAt;

    @Builder
    public Board(String title, String content, Date createdAt) {
        this.title = title;
        this.content = content;
    }

}
