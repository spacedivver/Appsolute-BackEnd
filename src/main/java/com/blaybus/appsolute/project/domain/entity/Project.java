package com.blaybus.appsolute.project.domain.entity;

import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "PROJECT")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="project_id")
    private Long projectId;

    @Column(name="month")
    private int month;

    @Column(name="day")
    private int day;

    @Column(name="project_name")
    private String projectName;

    @Column(name="granted_point")
    private Long grantedPoint;

    @Column(name="note",columnDefinition = "TEXT")
    private String note;

    @Column(name="user_id")
    private Long userId;

    @Column(name="year")
    private int year;

    public void updateGrantedPoint(Long grantedPoint) {
        if (grantedPoint == null || grantedPoint < 0) {
            throw new ApplicationException(
                    ErrorStatus.toErrorStatus("유효하지 않은 포인트 값입니다.", 400, LocalDateTime.now())
            );
        }
        this.grantedPoint = grantedPoint;
    }

    public void updateMonth(int month) {
        this.month = month;
    }

    public void updateDay(int day) {
        this.day = day;
    }

    public void updateProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void updateNote(String note) {
        this.note = note;
    }
}
