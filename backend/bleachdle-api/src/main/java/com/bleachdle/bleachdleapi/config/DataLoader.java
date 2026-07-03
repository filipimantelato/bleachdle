package com.bleachdle.bleachdleapi.config;

import com.bleachdle.bleachdleapi.repository.CharacterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

    }

    private final CharacterRepository repository;

    public DataLoader(CharacterRepository repository) {
        this.repository = repository;
    }
}