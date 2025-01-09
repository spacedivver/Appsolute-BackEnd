package com.blaybus.appsolute.project.repository;

import com.blaybus.appsolute.project.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByUserId(Long userId);
}
