package com.blaybus.appsolute.xp.domain.entity;

import com.blaybus.appsolute.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "XP")
public class Xp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "xp_id")
    private Long xpId;

    @Column(name = "year")
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Xp(Long xpId, Integer year, User user) {
        this.xpId = xpId;
        this.year = year;
        this.user = user;
    }
}
