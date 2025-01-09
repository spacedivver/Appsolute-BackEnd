package com.blaybus.appsolute.project.service;

import com.blaybus.appsolute.project.domain.entity.Project;
import com.blaybus.appsolute.project.domain.response.ProjectResponse;
import com.blaybus.appsolute.project.repository.JpaProjectRepository;
import com.blaybus.appsolute.user.domain.entity.User;
import com.blaybus.appsolute.user.repository.JpaUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {

    private JpaUserRepository userRepository;

    private JpaProjectRepository projectRepository;

    public void saveProject(Project project, String employeeNumber) {

        User user = userRepository.findByEmployeeNumber(employeeNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        project.setUserId(user.getId());

        projectRepository.save(project);
    }

    public List<ProjectResponse> getProjectsByUser(Long userId) {
        return projectRepository.findByUserId(userId).stream()
                .map(ProjectResponse::fromEntity)
                .collect(Collectors.toList());
    }

}