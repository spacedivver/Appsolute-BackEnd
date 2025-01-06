package com.blaybus.appsolute.departmentleader.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="DEPARTMENT_LEADER")
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class DepartmentLeader {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="department_leader_id")
    private Long departmentLeaderId;

    @ManyToOne
    @JoinColumn(name="department_id")
    private Department departmentId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
