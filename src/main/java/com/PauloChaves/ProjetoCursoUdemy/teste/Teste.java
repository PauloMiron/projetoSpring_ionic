package com.PauloChaves.ProjetoCursoUdemy.teste;

import com.PauloChaves.ProjetoCursoUdemy.entities.Categoria;
import com.PauloChaves.ProjetoCursoUdemy.entities.Produto;
import com.PauloChaves.ProjetoCursoUdemy.repository.CategoriaRepository;
import com.PauloChaves.ProjetoCursoUdemy.repository.ProdutoRepository;
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
        @Autowired
        private ProdutoRepository produtoRepository;

        @Override
        public void run(String... args) throws Exception {
            Categoria cat1 = new Categoria(null, "Informatica");
            Categoria cat2 = new Categoria(null, "Escritorio");

            Produto p1 = new Produto(null,"Computador",2000.00);
            Produto p2 = new Produto(null,"Impressora",800.00);
            Produto p3 = new Produto(null,"Mouse",80.00);


            cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
            cat2.getProdutos().addAll(Arrays.asList(p2));

            p1.getCategorias().addAll(Arrays.asList(cat1));
            p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
            p3.getCategorias().addAll(Arrays.asList(cat1));

            categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
            produtoRepository.saveAll(Arrays.asList(p1,p2,p3));


        }
    }

