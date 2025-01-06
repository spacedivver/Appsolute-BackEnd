package com.blaybus.appsolute.xp.domain;

import com.blaybus.appsolute.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "XP")
public class XP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "xp_id")
    private Long xpId;

    @Column(name = "year")
    private Integer year;

    @Column(name = "point")
    private Long point;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
