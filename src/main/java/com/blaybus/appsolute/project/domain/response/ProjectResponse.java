package com.blaybus.appsolute.project.domain.response;

import com.blaybus.appsolute.project.domain.entity.Project;
import lombok.Data;

@Data
public class ProjectResponse {
    private Long projectId;
    private Integer month;
    private Integer day;
    private String projectName;
    private Long grantedPoint;
    private String notes;

    // 엔티티 -> DTO 변환
    public static ProjectResponse fromEntity(Project project) {
        ProjectResponse response = new ProjectResponse();
        response.setProjectId(project.getProjectId());
        response.setMonth(project.getMonth());
        response.setDay(project.getDay());
        response.setProjectName(project.getProjectName());
        response.setGrantedPoint(project.getGrantedPoint());
        response.setNotes(project.getNotes());
        return response;
    }
}
