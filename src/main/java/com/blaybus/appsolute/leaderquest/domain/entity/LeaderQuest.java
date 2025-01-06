package com.blaybus.appsolute.leaderquest.domain.entity;

import com.blaybus.appsolute.departmentleader.domain.DepartmentLeader;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="LEADER_QUEST")
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class LeaderQuest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="leader_quest_id")
    private Long leaderQuestId;

    @Column(name="leader_quest_name")
    private String leaderQuestName;

    // Enum 타입 정의
    public enum LeaderQuestType {
        WEEKLY,MONTHLY // 예시 타입
    }

    @Column(name = "leader_quest_type")
    @Enumerated(EnumType.STRING) // Enum 타입을 문자열로 저장
    private LeaderQuestType leaderQuestType;

    @Column(name="max_threshold")
    private String maxThreshold;

    @Column(name = "medium_threshold")
    private String mediumThreshold;

    @Column(name = "quest_status")
    private String questStatus;

    @Column(name="actual_point")
    private String actualPoint;

    @ManyToOne
    @JoinColumn(name="department_leader_id")
    private DepartmentLeader departmentLeaderId;

    @Column(name = "medium_point")
    private String mediumPoint;

    @Column(name="max_point")
    private String maxPoint;

    @Column(name="year")
    private String year;

}
