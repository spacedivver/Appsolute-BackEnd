package com.blaybus.appsolute.xp.repository;

import com.blaybus.appsolute.xp.domain.XP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaXPRepository extends JpaRepository<XP, Long> {
    List<XP> findByUser_Id(Long userId);
    Optional<XP> findByUser_IdAndYear(Long userId, Integer year);
}
