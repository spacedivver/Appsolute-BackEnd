package com.blaybus.appsolute.xp.repository;

import com.blaybus.appsolute.xp.domain.entity.Xp;
import com.blaybus.appsolute.xp.domain.entity.XpDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaXpDetailRepository extends JpaRepository<XpDetail, Long> {
    List<XpDetail> findByXp(Xp xp);
    void deleteByXp(Xp xp);
}
