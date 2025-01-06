package com.blaybus.appsolute.character.repository;

import com.blaybus.appsolute.character.domain.entity.Characters;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCharacterRepository extends JpaRepository<Characters, Long> {
}
