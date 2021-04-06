package com.PauloChaves.ProjetoCursoUdemy.resources;

import com.PauloChaves.ProjetoCursoUdemy.entities.Categoria;
import com.PauloChaves.ProjetoCursoUdemy.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    @GetMapping(value="{id}")
    public ResponseEntity<Categoria> findById(@PathVariable Long id){
        Categoria obj = service.buscar(id);

        return ResponseEntity.ok().body(obj);
    }
}
