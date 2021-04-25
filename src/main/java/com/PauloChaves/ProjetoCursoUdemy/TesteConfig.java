package com.PauloChaves.ProjetoCursoUdemy;

import com.PauloChaves.ProjetoCursoUdemy.services.DBservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;


@Configuration
@Profile("test")
public class TesteConfig {

    @Autowired
    private DBservice dBservice;

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        dBservice.instantiateTestDatabase();

        return true;
    }


    }

