package com.PauloChaves.ProjetoCursoUdemy.services;

import com.PauloChaves.ProjetoCursoUdemy.entities.Categoria;
import com.PauloChaves.ProjetoCursoUdemy.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria buscar(Long id){
        Optional<Categoria> obj = repo.findById(id);
        return obj.orElse(null);

    }
}
