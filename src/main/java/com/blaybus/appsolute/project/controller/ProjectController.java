package com.blaybus.appsolute.project.controller;

import com.blaybus.appsolute.project.domain.request.ProjectRequest;
import com.blaybus.appsolute.project.domain.response.ProjectResponse;
import com.blaybus.appsolute.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
@Tag(name="전사 프로젝트 api")
public class ProjectController {
    private final ProjectService projectService;

    @Operation(summary="전사 프로젝트 정보 조회")
    @GetMapping("/user/{userId}")
    public List<ProjectResponse> getProjectByUser(@PathVariable String userId) {
        return projectService.getProjectByUser(userId);
    }

    @Operation(summary="전사 프로젝트 정보 저장")
    @PostMapping("/save")
    public ResponseEntity<Void> updateProject(@RequestBody ProjectRequest projectRequest) {
        projectService.updateProject(projectRequest);
        return ResponseEntity.ok().build();
    }

}
