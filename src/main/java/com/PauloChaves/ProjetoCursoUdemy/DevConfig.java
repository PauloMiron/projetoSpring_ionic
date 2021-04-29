package com.PauloChaves.ProjetoCursoUdemy;

import com.PauloChaves.ProjetoCursoUdemy.services.DBservice;
import com.PauloChaves.ProjetoCursoUdemy.services.EmailService;
import com.PauloChaves.ProjetoCursoUdemy.services.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;


@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DBservice dBservice;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instantiateDatabase() throws ParseException {

        if(!"create".equals(strategy)){
            return false;
        }
        dBservice.instantiateTestDatabase();

        return true;
    }

    @Bean
    public EmailService emailService(){
        return new SmtpEmailService();
    }



}

