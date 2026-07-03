package com.bleachdle.bleachdleapi.service;

import com.bleachdle.bleachdleapi.entity.Character;
import com.bleachdle.bleachdleapi.repository.CharacterRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class CharacterDataLoader implements CommandLineRunner {

    private final CharacterRepository repository;

    public CharacterDataLoader(CharacterRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {

        if(repository.count() > 0){
            return;
        }

        ObjectMapper mapper = new ObjectMapper();

        InputStream input =
                new ClassPathResource("data/characters.json")
                        .getInputStream();

        List<Character> characters =
                mapper.readValue(
                        input,
                        new TypeReference<List<Character>>() {}
                );

        characters.forEach(c -> c.setId(null));
        repository.saveAll(characters);

        System.out.println("Personagens importados!");
    }
}