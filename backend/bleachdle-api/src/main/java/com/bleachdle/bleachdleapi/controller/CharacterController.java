package com.bleachdle.bleachdleapi.controller;

import com.bleachdle.bleachdleapi.dto.*;
import com.bleachdle.bleachdleapi.entity.Character;
import com.bleachdle.bleachdleapi.entity.GameSession;
import com.bleachdle.bleachdleapi.service.CharacterService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/characters")
@CrossOrigin(origins = "*")
public class CharacterController {

    private final CharacterService service;

    public CharacterController(CharacterService service) {
        this.service = service;
    }

    @GetMapping
    public List<Character> getAll() {
        return service.findAll();
    }

    @GetMapping("/search")
    public List<CharacterSearchDTO> searchCharacters() {

        return service.findAll()
                .stream()
                .map(character ->
                        new CharacterSearchDTO(
                                character.getId(),
                                character.getName(),
                                character.getImage()
                        )
                )
                .toList();
    }

    @PostMapping("/guess")
    public GuessResponseDTO guess(
            @RequestBody GuessRequestDTO request
    ){
        System.out.println(
                request.getPlayerId()
        );
        return service.checkGuess(request);
    }

    @GetMapping("/game-state")
    public GameStateDTO getGameState(){

        return new GameStateDTO(
                false,
                null
        );
    }

    @GetMapping("/progress/{playerId}")
    public ProgressDTO getProgress(
            @PathVariable String playerId
    ){
        return service.getProgress(playerId);
    }
}