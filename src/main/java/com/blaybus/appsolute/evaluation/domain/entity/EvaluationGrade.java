package com.blaybus.appsolute.evaluation.domain.entity;

import com.blaybus.appsolute.evaluation.domain.type.GradeType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "EVALUATION_GRADE")
public class EvaluationGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_grade_id")
    private Long evaluationGradeId;

    @Column(name = "evaluation_grade_name")
    @Enumerated(EnumType.STRING)
    private GradeType evaluationGradeName;

    @Column(name = "evaluation_grade_point")
    private Long evaluationGradePoint;
}
