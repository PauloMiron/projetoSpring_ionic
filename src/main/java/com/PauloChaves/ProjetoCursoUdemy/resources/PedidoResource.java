package com.PauloChaves.ProjetoCursoUdemy.resources;

import com.PauloChaves.ProjetoCursoUdemy.entities.Pedido;
import com.PauloChaves.ProjetoCursoUdemy.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService service;

    @GetMapping(value="{id}")
    public ResponseEntity<Pedido> findById(@PathVariable Long id){
        Pedido obj = service.buscar(id);

        return ResponseEntity.ok().body(obj);
    }
}
