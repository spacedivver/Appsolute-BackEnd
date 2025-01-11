package com.blaybus.appsolute.character.controller;

import com.blaybus.appsolute.character.domain.response.ReadCharacterResponse;
import com.blaybus.appsolute.character.service.CharacterService;
import com.blaybus.appsolute.user.domain.response.LoginUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/characters")
@Tag(name = "유저 API", description = "유저 API")
public class CharacterController {

    private final CharacterService characterService;

    @Operation(summary = "전체 캐릭터 조회", description = "전체 캐릭터를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캐릭터 조회 성공", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ReadCharacterResponse>> getAllCharacters() {
        return ResponseEntity.ok(characterService.getAllCharacters());
    }
}
