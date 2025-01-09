package com.blaybus.appsolute.leaderquest.repository;

import com.blaybus.appsolute.leaderquest.domain.entity.LeQuestBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaLeQuestBoardRepository extends JpaRepository<LeQuestBoard, Long> {
    List<LeQuestBoard> findByUserIdAndLeaderQuest_Period(Long userId, Integer period);
}
