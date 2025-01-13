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

    @Operation(summary="전사 프로젝트 정보 저장")
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

    @Operation(summary="전사 프로젝트 정보 조회")
    @GetMapping("/user/{userId}")
    public List<ProjectResponse> getProjectByUser(@PathVariable Long userId) {
        return projectService.getProjectByUser(userId);
    }

    @PutMapping
    public ResponseEntity<Void> updateProjectXP(ProjectRequest projectRequest) {
        projectService.updateProjectXP(projectRequest);
        return ResponseEntity.ok().build();
    }

}
