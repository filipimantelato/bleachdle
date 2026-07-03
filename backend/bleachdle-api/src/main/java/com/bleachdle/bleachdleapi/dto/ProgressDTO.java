package com.bleachdle.bleachdleapi.dto;

public class ProgressDTO {

    private boolean solved;
    private String characterName;
    private String characterImage;
    private Integer attempts;

    public ProgressDTO() {
    }

    public ProgressDTO(
            boolean solved,
            String characterName,
            String characterImage,
            Integer attempts
    ) {
        this.solved = solved;
        this.characterName = characterName;
        this.characterImage = characterImage;
        this.attempts = attempts;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
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

    public String getCharacterImage() {
        return characterImage;
    }

    public void setCharacterImage(String characterImage) {
        this.characterImage = characterImage;
    }
}