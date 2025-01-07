package com.blaybus.appsolute.departmentgroupquest.domain.entity;

import com.blaybus.appsolute.departmentgroup.domain.DepartmentGroup;
import com.blaybus.appsolute.departmentgroupquest.domain.type.QuestStatusType;
import com.blaybus.appsolute.departmentgroupquest.domain.type.QuestType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "DEPARTMENT_GROUP_QUEST")
public class DepartmentGroupQuest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentGroupQuestId;

    @Column(name = "department_quest_name")
    private String departmentQuestName;

    @Enumerated(EnumType.STRING)
    @Column(name = "department_quest_type")
    private QuestType departmentQuestType;

    @Column(name = "max_threshold")
    private Double maxThreshold;

    @Column(name = "medium_threshold")
    private Double mediumThreshold;

    @Column(name = "productivity")
    private Double productivity;

    @Column(name = "quest_stats")
    private QuestStatusType departmentGroupQuestStatus;

    @Column(name = "medium_point")
    private Long mediumPoint;

    @Column(name = "max_point")
    private Long maxPoint;

    @ManyToOne
    @JoinColumn(name = "department_group_id")
    private DepartmentGroup departmentGroup;

    @Builder
    public DepartmentGroupQuest(Long departmentGroupQuestId, String departmentQuestName, QuestType departmentQuestType, Double maxThreshold, Double mediumThreshold, Double productivity, QuestStatusType departmentGroupQuestStatus, Long mediumPoint, Long maxPoint, DepartmentGroup departmentGroup) {
        this.departmentGroupQuestId = departmentGroupQuestId;
        this.departmentQuestName = departmentQuestName;
        this.departmentQuestType = departmentQuestType;
        this.maxThreshold = maxThreshold;
        this.mediumThreshold = mediumThreshold;
        this.productivity = productivity;
        this.departmentGroupQuestStatus = departmentGroupQuestStatus;
        this.mediumPoint = mediumPoint;
        this.maxPoint = maxPoint;
        this.departmentGroup = departmentGroup;
    }
}
