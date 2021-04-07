package com.PauloChaves.ProjetoCursoUdemy.repository;

import com.PauloChaves.ProjetoCursoUdemy.entities.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade,Long> {
}
