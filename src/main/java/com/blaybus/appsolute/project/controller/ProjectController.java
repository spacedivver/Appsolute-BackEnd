package com.blaybus.appsolute.project.controller;

import com.blaybus.appsolute.project.domain.entity.Project;
import com.blaybus.appsolute.project.domain.request.ProjectRequest;
import com.blaybus.appsolute.project.domain.response.ProjectResponse;
import com.blaybus.appsolute.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public String saveProject(@RequestBody ProjectRequest projectRequest) {

        Project project = new Project();
        project.setMonth(projectRequest.getMonth());
        project.setDay(projectRequest.getDay());
        project.setProjectName(projectRequest.getProjectName());
        project.setGrantedPoint(projectRequest.getGrantedPoint());
        project.setNotes(projectRequest.getNotes());

        projectService.saveProject(project, projectRequest.getEmployeeNumber());
        return "Project saved successfully!";
    }

    @GetMapping("/user/{userId}")
    public List<ProjectResponse> getProjectsByUser(@PathVariable Long userId) {
        return projectService.getProjectsByUser(userId);
    }
}
