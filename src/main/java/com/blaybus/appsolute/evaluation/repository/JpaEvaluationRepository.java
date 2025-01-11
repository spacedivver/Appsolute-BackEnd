package com.blaybus.appsolute.evaluation.repository;

import com.blaybus.appsolute.evaluation.domain.entity.Evaluation;
import com.blaybus.appsolute.evaluation.domain.type.PeriodType;
import com.blaybus.appsolute.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaEvaluationRepository extends JpaRepository<Evaluation, Long> {
    Optional<Evaluation> findByUserAndYear(User user, Integer year);
    List<Evaluation> findByUser(User user);
    void deleteByUserAndYearAndPeriodType(User user, Integer year, PeriodType period);
}
