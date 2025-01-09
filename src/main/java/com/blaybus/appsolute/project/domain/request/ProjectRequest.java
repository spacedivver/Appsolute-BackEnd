package com.blaybus.appsolute.project.domain.request;

import lombok.Data;

@Data
public class ProjectRequest {

    private Integer month;
    private Integer day;
    private String employeeNumber;
    private String projectName;
    private Long grantedPoint;
    private String notes;

}
