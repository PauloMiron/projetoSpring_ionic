package com.PauloChaves.ProjetoCursoUdemy.resources;


import com.PauloChaves.ProjetoCursoUdemy.dto.ClienteDTO;
import com.PauloChaves.ProjetoCursoUdemy.entities.Cliente;
import com.PauloChaves.ProjetoCursoUdemy.services.ClienteService;
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
@RequestMapping(value= "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> fildAll(){
        List<Cliente> list = service.findAll();
        List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value="/page")
    public ResponseEntity<Page<ClienteDTO>> findPage(
            @RequestParam(value = "page",defaultValue = "0")Integer page,
            @RequestParam (value = "linesPerPage",defaultValue = "24")Integer LinesPerPage,
            @RequestParam (value = "orderBy",defaultValue = "nome")String orderBy,
            @RequestParam (value = "direction",defaultValue = "ASC")String direction){
        Page<Cliente> list = service.findPage(page,LinesPerPage,orderBy,direction);
        Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));

        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value="{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id){
        Cliente obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Cliente> insert(@Valid @RequestBody ClienteDTO objDto){
        Cliente obj = service.fromDTO(objDto);
        obj =  service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id,@Valid @RequestBody ClienteDTO objDto){
        Cliente obj = service.fromDTO(objDto);
        obj = service.update(id,obj);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
