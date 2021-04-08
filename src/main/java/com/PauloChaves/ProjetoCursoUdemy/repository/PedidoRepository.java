package com.PauloChaves.ProjetoCursoUdemy.repository;


import com.PauloChaves.ProjetoCursoUdemy.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long> {
}
