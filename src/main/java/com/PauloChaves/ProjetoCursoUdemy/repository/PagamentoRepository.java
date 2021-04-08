package com.PauloChaves.ProjetoCursoUdemy.repository;


import com.PauloChaves.ProjetoCursoUdemy.entities.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento,Long> {
}
