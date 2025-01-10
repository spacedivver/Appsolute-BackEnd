package com.blaybus.appsolute.evaluation.domain.entity;

import com.blaybus.appsolute.evaluation.domain.type.PeriodType;
import com.blaybus.appsolute.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "EVALUATION")
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id")
    private Long evaluationId;

    @Column(name = "year")
    private Integer year;

    @Column(name = "period_type")
    @Enumerated(EnumType.STRING)
    private PeriodType periodType;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "evaluation_grade_id")
    private EvaluationGrade evaluationGrade;

    public void updateEvaluationGrade(EvaluationGrade evaluationGrade) {
        this.evaluationGrade = evaluationGrade;
    }

    @Builder
    public Evaluation(Long evaluationId, Integer year, PeriodType periodType, String notes, User user, EvaluationGrade evaluationGrade) {
        this.evaluationId = evaluationId;
        this.year = year;
        this.periodType = periodType;
        this.notes = notes;
        this.user = user;
        this.evaluationGrade = evaluationGrade;
    }
}
