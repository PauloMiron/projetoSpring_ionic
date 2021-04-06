package com.PauloChaves.ProjetoCursoUdemy.teste;

import com.PauloChaves.ProjetoCursoUdemy.entities.Categoria;
import com.PauloChaves.ProjetoCursoUdemy.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


import java.util.Arrays;

@Configuration
@Profile("test")
public class Teste implements CommandLineRunner{

        @Autowired
        private CategoriaRepository categoriaRepository;

        @Override
        public void run(String... args) throws Exception {
            Categoria cat1 = new Categoria(null, "Informatica");
            Categoria cat2 = new Categoria(null, "Escritorio");

            categoriaRepository.saveAll(Arrays.asList(cat1,cat2));

        }
    }

