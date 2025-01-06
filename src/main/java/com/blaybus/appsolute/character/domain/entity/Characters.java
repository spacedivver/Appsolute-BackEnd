package com.blaybus.appsolute.character.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "CHARACTERS")
public class Characters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id")
    private Long characterId;

    @Column(name = "character_name", columnDefinition = "VARCHAR(20)")
    private String characterName;

    @Column(name = "character_image", columnDefinition = "TEXT")
    private String characterImage;
}
