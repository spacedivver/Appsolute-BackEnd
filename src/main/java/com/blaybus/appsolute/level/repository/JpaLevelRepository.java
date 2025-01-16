package com.blaybus.appsolute.level.repository;

import com.blaybus.appsolute.level.domain.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaLevelRepository extends JpaRepository<Level, Long> {
    Optional<Level> findByLevelName(String levelName);
    Optional<Level> findByNextLevel(Level nextLevel);
}
