package com.PauloChaves.ProjetoCursoUdemy.resources;


import com.PauloChaves.ProjetoCursoUdemy.entities.Pedido;
import com.PauloChaves.ProjetoCursoUdemy.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService service;

    @GetMapping(value="{id}")
    public ResponseEntity<Pedido> findById(@PathVariable Long id){
        Pedido obj = service.find(id);

        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Pedido> insert(@Valid @RequestBody Pedido obj){
        obj =  service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }
}
