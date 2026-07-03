package com.bleachdle.bleachdleapi.dto;

import java.util.List;

public class GuessResponseDTO {

    private String name;
    private String gender;
    private String ageGroup;
    private String race;
    private String height;
    private String hairColor;
    private String firstLocation;
    private String status;
    private Long id;
    private String image;
    private Integer heightValue;
    private String guessedName;
    private String guessedGender;
    private String guessedAgeGroup;
    private List<String> guessedRace;
    private String guessedHairColor;
    private String guessedFirstLocation;
    private String guessedStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuessedStatus() {
        return guessedStatus;
    }

    public void setGuessedStatus(String guessedStatus) {
        this.guessedStatus = guessedStatus;
    }

    public String getGuessedFirstLocation() {
        return guessedFirstLocation;
    }

    public void setGuessedFirstLocation(String guessedFirstLocation) {
        this.guessedFirstLocation = guessedFirstLocation;
    }

    public String getGuessedHairColor() {
        return guessedHairColor;
    }

    public void setGuessedHairColor(String guessedHairColor) {
        this.guessedHairColor = guessedHairColor;
    }

    public List<String> getGuessedRace() {
        return guessedRace;
    }

    public void setGuessedRace(List<String> guessedRace) {
        this.guessedRace = guessedRace;
    }

    public String getGuessedAgeGroup() {
        return guessedAgeGroup;
    }

    public void setGuessedAgeGroup(String guessedAgeGroup) {
        this.guessedAgeGroup = guessedAgeGroup;
    }

    public String getGuessedGender() {
        return guessedGender;
    }

    public void setGuessedGender(String guessedGender) {
        this.guessedGender = guessedGender;
    }

    public String getGuessedName() {
        return guessedName;
    }

    public void setGuessedName(String guessedName) {
        this.guessedName = guessedName;
    }

    public Integer getHeightValue() {
        return heightValue;
    }

    public void setHeightValue(Integer heightValue) {
        this.heightValue = heightValue;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public GuessResponseDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getFirstLocation() {
        return firstLocation;
    }

    public void setFirstLocation(String firstLocation) {
        this.firstLocation = firstLocation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
// gerar getters e setters
}