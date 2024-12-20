package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ContactRepository repository) {

        return args -> {
            log.info("Preloading {}", repository.save(new Contact("Bilbo", "Baggins", "1", "BagEnd, Shire")));
            log.info("Preloading {}", repository.save(new Contact("Frodo", "Baggins", "2", "Mount Doom, Mordor")));
        };
    }
}