package com.PauloChaves.ProjetoCursoUdemy.repository;

import com.PauloChaves.ProjetoCursoUdemy.entities.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado,Long> {
}
