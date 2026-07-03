package com.bleachdle.bleachdleapi.dto;

public class GameStateDTO {

    private boolean solved;
    private String characterName;

    public GameStateDTO() {
    }

    public GameStateDTO(
            boolean solved,
            String characterName
    ) {
        this.solved = solved;
        this.characterName = characterName;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
}