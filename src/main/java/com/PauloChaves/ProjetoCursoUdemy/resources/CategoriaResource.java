package com.PauloChaves.ProjetoCursoUdemy.resources;

import com.PauloChaves.ProjetoCursoUdemy.entities.Categoria;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

    @GetMapping
    public List<Categoria> listar(){

        Categoria cat1 = new Categoria(1L,"Informatica");
        Categoria cat2 = new Categoria(2L,"Escritorio");

        List<Categoria> lista = new ArrayList<>();
        lista.add(cat1);
        lista.add(cat2);

        return lista;
    }
}
