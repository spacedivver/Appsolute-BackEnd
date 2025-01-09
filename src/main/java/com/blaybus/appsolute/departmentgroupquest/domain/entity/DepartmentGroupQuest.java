package com.blaybus.appsolute.departmentgroupquest.domain.entity;

import com.blaybus.appsolute.departmentgroup.domain.DepartmentGroup;
import com.blaybus.appsolute.departmentgroupquest.domain.type.QuestStatusType;
import com.blaybus.appsolute.departmentgroupquest.domain.type.QuestType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "DEPARTMENT_GROUP_QUEST")
public class DepartmentGroupQuest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentGroupQuestId;

    @Enumerated(EnumType.STRING)
    @Column(name = "department_quest_type")
    private QuestType departmentQuestType;

    @Column(name = "max_threshold")
    private Double maxThreshold;

    @Column(name = "medium_threshold")
    private Double mediumThreshold;

    @Column(name = "quest_stats")
    private QuestStatusType departmentGroupQuestStatus;

    @Column(name = "medium_point")
    private Long mediumPoint;

    @Column(name = "max_point")
    private Long maxPoint;

    @ManyToOne
    @JoinColumn(name = "department_group_id")
    private DepartmentGroup departmentGroup;

    @Column(name = "year")
    private Integer year;

    @Column(name = "month")
    private Integer month;

    @Column(name = "week")
    private Integer week;

    @Column(name = "nowXP")
    private Long nowXP;

    @Column(name = "note")
    private String note;

    public void updateNowXP(Long nowXP) {
        this.nowXP = nowXP;

        if(Objects.equals(nowXP, maxPoint)) {
            departmentGroupQuestStatus = QuestStatusType.MAX_COMPLETE;
        } else if(Objects.equals(nowXP, mediumPoint)) {
            departmentGroupQuestStatus = QuestStatusType.MEDIUM_COMPLETE;
        } else {
            departmentGroupQuestStatus = QuestStatusType.INCOMPLETE;
        }
    }

    @Builder
    public DepartmentGroupQuest(Long departmentGroupQuestId, QuestType departmentQuestType, Double maxThreshold, Double mediumThreshold, QuestStatusType departmentGroupQuestStatus, Long mediumPoint, Long maxPoint, DepartmentGroup departmentGroup, Integer year, Integer month, Integer week, Long nowXP, String note) {
        this.departmentGroupQuestId = departmentGroupQuestId;
        this.departmentQuestType = departmentQuestType;
        this.maxThreshold = maxThreshold;
        this.mediumThreshold = mediumThreshold;
        this.departmentGroupQuestStatus = departmentGroupQuestStatus;
        this.mediumPoint = mediumPoint;
        this.maxPoint = maxPoint;
        this.departmentGroup = departmentGroup;
        this.year = year;
        this.month = month;
        this.week = week;
        this.nowXP = nowXP;
        this.note = note;
    }
}
