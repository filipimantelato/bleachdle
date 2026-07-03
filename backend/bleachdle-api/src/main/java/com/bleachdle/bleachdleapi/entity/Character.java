package com.bleachdle.bleachdleapi.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "characters")
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "character_aliases",
            joinColumns = @JoinColumn(name = "character_id")
    )
    @Column(name = "alias")
    private List<String> aliases;

    private String gender;

    private String ageGroup;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "character_races",
            joinColumns = @JoinColumn(name = "character_id")
    )
    @Column(name = "race")
    private List<String> race;

    private Integer height;

    private String hairColor;

    private String firstLocation;

    private String status;

    private String image;

    public Character() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
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

    public List<String> getRace() {
        return race;
    }

    public void setRace(List<String> race) {
        this.race = race;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}