package com.blaybus.appsolute.project.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "PROJECT")
@RequiredArgsConstructor
@Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    private Integer month;
    private Integer day;
    private String projectName;
    private Long grantedPoint;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private Long userId;
}
