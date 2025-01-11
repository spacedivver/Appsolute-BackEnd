package com.blaybus.appsolute.project.controller;

import com.blaybus.appsolute.project.domain.entity.Project;
import com.blaybus.appsolute.project.domain.request.ProjectRequest;
import com.blaybus.appsolute.project.domain.response.ProjectResponse;
import com.blaybus.appsolute.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<String> saveProject(@RequestBody ProjectRequest projectRequest) {
        try {
            projectService.saveProject(projectRequest);
            return ResponseEntity.ok("성공적으로 저장되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("잘못된 요청 입니다."+ e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류가 발생했습니다."+ e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public List<ProjectResponse> getProjectsByUser(@PathVariable Long userId) {
        return projectService.getProjectsByUser(userId);
    }
}
