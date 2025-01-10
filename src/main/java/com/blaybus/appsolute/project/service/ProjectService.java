package com.blaybus.appsolute.project.service;

import com.blaybus.appsolute.project.domain.entity.Project;
import com.blaybus.appsolute.project.domain.request.ProjectRequest;
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

    public void saveProject(ProjectRequest projectRequest) {

        User user = userRepository.findByEmployeeNumber(projectRequest.getEmployeeNumber())
                .orElseThrow(() -> new IllegalArgumentException("해당 사옹자가 없습니다."));

        Project project = new Project();
        project.setMonth(projectRequest.getMonth());
        project.setDay(projectRequest.getDay());
        project.setProjectName(projectRequest.getProjectName());
        project.setGrantedPoint(projectRequest.getGrantedPoint());
        project.setNotes(projectRequest.getNotes());
        project.setUserId(user.getId());

        projectRepository.save(project);
    }

    public List<ProjectResponse> getProjectsByUser(Long userId) {
        return projectRepository.findByUserId(userId).stream()
                .map(ProjectResponse::fromEntity)
                .collect(Collectors.toList());
    }

}