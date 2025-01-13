package com.blaybus.appsolute.character.domain.response;

import com.blaybus.appsolute.character.domain.entity.Characters;
import lombok.Builder;

@Builder
public record ReadCharacterResponse(
        String imageUrl,
        String characterName
) {
    public static ReadCharacterResponse fromEntity(Characters character) {
        return ReadCharacterResponse.builder()
                .imageUrl(character.getCharacterImage())
                .characterName(character.getCharacterName())
                .build();
    }
}
