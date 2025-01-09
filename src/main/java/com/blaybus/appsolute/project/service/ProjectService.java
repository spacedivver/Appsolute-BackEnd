package com.blaybus.appsolute.project.service;

import com.blaybus.appsolute.project.domain.entity.Project;
import com.blaybus.appsolute.project.repository.JpaProjectRepository;
import com.blaybus.appsolute.user.domain.entity.User;
import com.blaybus.appsolute.user.repository.JpaUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {

    private JpaUserRepository userRepository;

    private JpaProjectRepository projectRepository;

    public void saveProject(Project project, String employeeNumber) {

        User user = userRepository.findByEmployeeNumber(employeeNumber)
                .orElseThrow(() -> new IllegalArgumentException("User not found with employee number: " + employeeNumber));

        project.setUserId(user.getId());

        projectRepository.save(project);
    }

    public List<Project> getProjectsByUser(Long userId) {
        return projectRepository.findByUserId(userId);
    }

}