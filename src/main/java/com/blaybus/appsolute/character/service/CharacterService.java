package com.blaybus.appsolute.character.service;

import com.blaybus.appsolute.character.domain.response.ReadCharacterResponse;
import com.blaybus.appsolute.character.repository.JpaCharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterService {

    private final JpaCharacterRepository characterRepository;

    public List<ReadCharacterResponse> getAllCharacters() {
        return characterRepository.findAll().stream().map(ReadCharacterResponse::fromEntity).toList();
    }
}
