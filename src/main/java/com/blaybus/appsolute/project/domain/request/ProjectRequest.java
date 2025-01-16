package com.blaybus.appsolute.project.domain.request;

import lombok.Data;

@Data
public class ProjectRequest {

    private int month;
    private int day;
    private String employeeNumber;
    private String targetParson;
    private String projectName;
    private Long grantedPoint;
    private String note;

}
