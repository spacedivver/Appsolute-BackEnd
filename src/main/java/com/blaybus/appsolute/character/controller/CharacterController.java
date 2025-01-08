package com.blaybus.appsolute.character.controller;

import com.blaybus.appsolute.character.domain.response.ReadCharacterResponse;
import com.blaybus.appsolute.character.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/characters")
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping
    public ResponseEntity<List<ReadCharacterResponse>> getAllCharacters() {
        return ResponseEntity.ok(characterService.getAllCharacters());
    }
}
