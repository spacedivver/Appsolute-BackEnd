package com.blaybus.appsolute.xp.repository;

import com.blaybus.appsolute.xp.domain.entity.Xp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaXPRepository extends JpaRepository<Xp, Long> {
    List<Xp> findByUser_Id(Long userId);
    Optional<Xp> findByUser_IdAndYear(Long userId, Integer year);
}
