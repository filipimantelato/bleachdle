package com.bleachdle.bleachdleapi.service;

import com.bleachdle.bleachdleapi.dto.GuessRequestDTO;
import com.bleachdle.bleachdleapi.dto.GuessResponseDTO;
import com.bleachdle.bleachdleapi.dto.ProgressDTO;
import com.bleachdle.bleachdleapi.entity.Character;
import com.bleachdle.bleachdleapi.entity.GameSession;
import com.bleachdle.bleachdleapi.repository.CharacterRepository;
import com.bleachdle.bleachdleapi.repository.GameSessionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

import java.time.LocalDate;
import java.util.List;

@Service
public class CharacterService {

    private String compareRace(
            List<String> guessRace,
            List<String> answerRace
    ){

        if(guessRace.equals(answerRace)){
            return "correct";
        }

        boolean commonRace =
                guessRace.stream()
                        .anyMatch(answerRace::contains);

        return commonRace
                ? "partial"
                : "wrong";
    }

    private String compareHeight(
            Integer guess,
            Integer answer
    ){

        if(guess.equals(answer)){
            return "correct";
        }

        return guess < answer
                ? "up"
                : "down";
    }

    private final CharacterRepository repository;
    private final GameSessionRepository gameSessionRepository;

    public CharacterService(
            CharacterRepository repository,
            GameSessionRepository gameSessionRepository
    ) {
        this.repository = repository;
        this.gameSessionRepository = gameSessionRepository;
    }

    public List<Character> findAll() {
        return repository.findAll();
    }

    public Character getTodayCharacter() {

        List<Character> characters = repository.findAll();

        String date =
                LocalDate.now().toString();

        int index =
                Math.abs(date.hashCode()) %
                        characters.size();

        return characters.get(index);
    }

    public ProgressDTO getProgress(String playerId){

        GameSession session =
                gameSessionRepository
                        .findByPlayerIdAndGameDate(
                                playerId,
                                LocalDate.now()
                        )
                        .orElse(null);

        if(session == null){
            return new ProgressDTO(
                    false,
                    null,
                    null,
                    null
            );
        }

        return new ProgressDTO(
                true,
                session.getCharacterName(),
                session.getCharacterImage(),
                session.getAttempts()
        );
    }

    public GuessResponseDTO checkGuess(GuessRequestDTO request) {


        System.out.println("Nome recebido: " + request.getName());
        System.out.println("Player recebido: " + request.getPlayerId());

        if(request.getName() == null){
            throw new RuntimeException("Nome do personagem veio nulo");
        }

        Character answer = getTodayCharacter();

        System.out.println("Nome recebido: " + request.getName());

        Character guess = repository
                .findByName(request.getName())
                .orElseThrow(() -> new RuntimeException(
                        "Personagem não encontrado: " + request.getName()
                ));

        GuessResponseDTO response =
                new GuessResponseDTO();

        response.setName(
                guess.getName().equals(answer.getName())
                        ? "correct"
                        : "wrong"
        );

        response.setGender(
                guess.getGender().equals(answer.getGender())
                        ? "correct"
                        : "wrong"
        );

        response.setAgeGroup(
                guess.getAgeGroup().equals(answer.getAgeGroup())
                        ? "correct"
                        : "wrong"
        );



        response.setHairColor(
                guess.getHairColor().equals(answer.getHairColor())
                        ? "correct"
                        : "wrong"
        );

        response.setFirstLocation(
                guess.getFirstLocation().equals(answer.getFirstLocation())
                        ? "correct"
                        : "wrong"
        );

        response.setStatus(
                guess.getStatus().equals(answer.getStatus())
                        ? "correct"
                        : "wrong"
        );

        response.setHeight(
                compareHeight(
                        guess.getHeight(),
                        answer.getHeight()
                )
        );

        response.setRace(
                compareRace(
                        guess.getRace(),
                        answer.getRace()
                )
        );

        response.setId(guess.getId());
        response.setImage(guess.getImage());

        response.setGuessedName(
                guess.getName()
        );

        response.setGuessedGender(
                guess.getGender()
        );

        response.setGuessedAgeGroup(
                guess.getAgeGroup()
        );

        response.setGuessedRace(
                guess.getRace()
        );

        response.setHeightValue(
                guess.getHeight()
        );

        response.setGuessedHairColor(
                guess.getHairColor()
        );

        response.setGuessedFirstLocation(
                guess.getFirstLocation()
        );

        response.setGuessedStatus(
                guess.getStatus()
        );

        if(response.getName().equals("correct")){

            GameSession session = new GameSession();

            session.setCharacterName(
                    answer.getName()
            );

            session.setCharacterImage(
                    answer.getImage()
            );

            session.setPlayerId(
                    request.getPlayerId()
            );

            session.setGameDate(
                    LocalDate.now()
            );

            session.setSolved(true);
            session.setAttempts(
                    request.getAttempts()
            );

            gameSessionRepository.save(session);
        }

        return response;
    }

}