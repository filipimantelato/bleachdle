package com.bleachdle.bleachdleapi.dto;

public class CharacterSearchDTO {

    private Long id;
    private String name;
    private String image;

    public CharacterSearchDTO() {
    }

    public CharacterSearchDTO(Long id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}