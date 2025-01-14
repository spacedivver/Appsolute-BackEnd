package com.blaybus.appsolute.leaderquest.repository;

import com.blaybus.appsolute.leaderquest.domain.entity.LeQuestBoard;
import com.blaybus.appsolute.project.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaLeQuestBoardRepository extends JpaRepository<LeQuestBoard, Long> {
    List<LeQuestBoard> findByUserIdAndMonth(Long userId, Long month);
    Optional<LeQuestBoard> findByUserIdAndLeaderQuestId(Long userId, Long leaderQuestId);
    List<LeQuestBoard> findByUserId(Long userId);
}
