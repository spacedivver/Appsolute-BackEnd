package com.blaybus.appsolute.board.repository;

import com.blaybus.appsolute.board.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBoardRepository extends JpaRepository<Board, Long> {
}
