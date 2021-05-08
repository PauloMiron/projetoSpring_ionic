package com.PauloChaves.ProjetoCursoUdemy.resources;


import com.PauloChaves.ProjetoCursoUdemy.dto.CategoriaDTO;
import com.PauloChaves.ProjetoCursoUdemy.entities.Categoria;
import com.PauloChaves.ProjetoCursoUdemy.entities.Pedido;
import com.PauloChaves.ProjetoCursoUdemy.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping
    public ResponseEntity<Page<Pedido>> findPage(
            @RequestParam (value = "page",defaultValue = "0")Integer page,
            @RequestParam (value = "linesPerPage",defaultValue = "24")Integer LinesPerPage,
            @RequestParam (value = "orderBy",defaultValue = "instante")String orderBy,
            @RequestParam (value = "direction",defaultValue = "DESC")String direction) {
        Page<Pedido> list = service.findPage(page, LinesPerPage, orderBy, direction);

        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<Pedido> insert(@Valid @RequestBody Pedido obj){
        obj =  service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }


}
