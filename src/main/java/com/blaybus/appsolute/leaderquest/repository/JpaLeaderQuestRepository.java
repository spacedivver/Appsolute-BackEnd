package com.blaybus.appsolute.leaderquest.repository;

import com.blaybus.appsolute.leaderquest.domain.entity.LeaderQuest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaLeaderQuestRepository extends JpaRepository<LeaderQuest, Long> {
    Optional<LeaderQuest> findByName(String name);
}
