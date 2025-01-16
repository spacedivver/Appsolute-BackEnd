package com.blaybus.appsolute.project.domain.response;

import com.blaybus.appsolute.project.domain.entity.Project;
import lombok.Data;

@Data
public class ProjectResponse {
    private Long projectId;
    private int month;
    private int day;
    private String projectName;
    private Long grantedPoint;
    private String note;

    public static ProjectResponse fromEntity(Project project) {
        ProjectResponse response = new ProjectResponse();
        response.setProjectId(project.getProjectId());
        response.setMonth(project.getMonth());
        response.setDay(project.getDay());
        response.setProjectName(project.getProjectName());
        response.setGrantedPoint(project.getGrantedPoint());
        response.setNote(project.getNote());
        return response;
    }
}
