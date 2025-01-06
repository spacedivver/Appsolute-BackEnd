package com.blaybus.appsolute.leaderquest.repository;

import com.blaybus.appsolute.leaderquest.domain.entity.LeaderQuest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaderQuestRepository extends JpaRepository<LeaderQuest, Long> {
}
