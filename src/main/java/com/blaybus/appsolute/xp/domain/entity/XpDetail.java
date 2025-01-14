package com.blaybus.appsolute.xp.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "XP_DETAIL")
public class XpDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long xp_detail_id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "point")
    private Long point;

    @ManyToOne
    @JoinColumn(name = "xp_id")
    private Xp xp;

    public void updatePoint(Long point) {
        this.point = point;
    }

    @Builder
    public XpDetail(Long xp_detail_id, LocalDateTime createdAt, Long point, Xp xp) {
        this.xp_detail_id = xp_detail_id;
        this.createdAt = createdAt;
        this.point = point;
        this.xp = xp;
    }
}
