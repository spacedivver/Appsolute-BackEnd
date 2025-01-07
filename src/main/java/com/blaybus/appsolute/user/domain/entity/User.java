package com.blaybus.appsolute.user.domain.entity;

import com.blaybus.appsolute.character.domain.entity.Characters;
import com.blaybus.appsolute.departmentgroup.domain.DepartmentGroup;
import com.blaybus.appsolute.level.domain.entity.Level;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

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

    @Column(name = "password")
    private String password;

    @ManyToOne
    private DepartmentGroup departmentGroup;

    @ManyToOne
    private Characters characters;

    @ManyToOne
    private Level level;

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateCharacters(Characters newCharacters) {
        this.characters = newCharacters;
    }

    @Builder
    public User(Long id, String employeeNumber, String userName, Date joiningDate, String userId, String password, DepartmentGroup departmentGroup, Characters characters, Level level) {
        this.id = id;
        this.employeeNumber = employeeNumber;
        this.userName = userName;
        this.joiningDate = joiningDate;
        this.userId = userId;
        this.password = password;
        this.departmentGroup = departmentGroup;
        this.characters = characters;
        this.level = level;
    }
}
