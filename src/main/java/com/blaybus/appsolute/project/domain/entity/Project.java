package com.blaybus.appsolute.project.domain.entity;

import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    public void updateGrantedPoint(Long grantedPoint) {
        if (grantedPoint == null || grantedPoint < 0) {
            throw new ApplicationException(
                    ErrorStatus.toErrorStatus("유효하지 않은 포인트 값입니다.", 400, LocalDateTime.now())
            );
        }
        this.grantedPoint = grantedPoint;
    }
}
