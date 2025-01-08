package com.blaybus.appsolute.user.domain.entity;

import com.blaybus.appsolute.character.domain.entity.Characters;
import com.blaybus.appsolute.departmentgroup.domain.DepartmentGroup;
import com.blaybus.appsolute.fcm.domain.entity.FcmToken;
import com.blaybus.appsolute.fcm.domain.entity.NotificationLogs;
import com.blaybus.appsolute.level.domain.entity.Level;
import com.blaybus.appsolute.xp.domain.XP;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "employee_number", columnDefinition = "VARCHAR(10)", unique = true)
    private String employeeNumber;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "joining_date")
    private Date joiningDate;

    @Column(name = "user_id", unique = true)
    private String userId;

    @Column(name = "initial_password")
    private String initialPassword;

    @Column(name = "changed_password")
    private String changedPassword;

    @ManyToOne
    @JoinColumn(name = "department_group_id")
    private DepartmentGroup departmentGroup;

    @ManyToOne
    @JoinColumn(name = "character_id")
    private Characters characters;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<XP> xpList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FcmToken> fcmTokenList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationLogs> notificationLogs;

    public void updatePassword(String newPassword) {
        this.changedPassword = newPassword;
    }

    public void updateCharacters(Characters newCharacters) {
        this.characters = newCharacters;
    }

    public void updateLevel(Level level) {
        this.level = level;
    }

    public void updateDepartmentGroup(DepartmentGroup departmentGroup) {
        this.departmentGroup = departmentGroup;
    }

    public void updateUserName(String userName) {
        this.userName = userName;
    }

    public void updateEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    @Builder
    public User(Long id, String employeeNumber, String userName, Date joiningDate, String userId, String initialPassword, String changedPassword, DepartmentGroup departmentGroup, Characters characters, Level level) {
        this.id = id;
        this.employeeNumber = employeeNumber;
        this.userName = userName;
        this.joiningDate = joiningDate;
        this.userId = userId;
        this.initialPassword = initialPassword;
        this.changedPassword = changedPassword;
        this.departmentGroup = departmentGroup;
        this.characters = characters;
        this.level = level;
    }
}
