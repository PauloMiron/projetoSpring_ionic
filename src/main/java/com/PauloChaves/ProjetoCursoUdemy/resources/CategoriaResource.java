package com.PauloChaves.ProjetoCursoUdemy.resources;

import com.PauloChaves.ProjetoCursoUdemy.dto.CategoriaDTO;
import com.PauloChaves.ProjetoCursoUdemy.entities.Categoria;
import com.PauloChaves.ProjetoCursoUdemy.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> fildAll(){
        List<Categoria> list = service.findAll();
        List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value="/page")
    public ResponseEntity<Page<CategoriaDTO>> fildPage(
            @RequestParam (value = "page",defaultValue = "0")Integer page,
            @RequestParam (value = "linesPerPage",defaultValue = "24")Integer LinesPerPage,
            @RequestParam (value = "orderBy",defaultValue = "nome")String orderBy,
            @RequestParam (value = "direction",defaultValue = "ASC")String direction){
        Page<Categoria> list = service.findPage(page,LinesPerPage,orderBy,direction);
        Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));

        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value="{id}")
    public ResponseEntity<Categoria> findById(@PathVariable Long id){
        Categoria obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Categoria> insert(@Valid @RequestBody CategoriaDTO objDto){
        Categoria obj = service.fromDTO(objDto);
        obj =  service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Categoria> update(@PathVariable Long id,@Valid @RequestBody CategoriaDTO objDto){
        Categoria obj = service.fromDTO(objDto);
        obj = service.update(id,obj);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }



}
