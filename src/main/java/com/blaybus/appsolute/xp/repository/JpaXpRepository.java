package com.blaybus.appsolute.xp.repository;

import com.blaybus.appsolute.user.domain.entity.User;
import com.blaybus.appsolute.xp.domain.entity.Xp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaXpRepository extends JpaRepository<Xp, Long> {
    List<Xp> findByUser_Id(Long userId);
    Optional<Xp> findByUser_IdAndYear(Long userId, Integer year);
    Optional<Xp> findByUserAndYear(User user, Integer year);
}
