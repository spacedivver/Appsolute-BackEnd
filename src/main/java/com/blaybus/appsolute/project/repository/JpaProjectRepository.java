package com.blaybus.appsolute.project.repository;

import com.blaybus.appsolute.project.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProjectRepository extends JpaRepository<Project, Long> {
}
