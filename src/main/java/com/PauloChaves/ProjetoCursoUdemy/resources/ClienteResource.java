package com.PauloChaves.ProjetoCursoUdemy.resources;


import com.PauloChaves.ProjetoCursoUdemy.entities.Cliente;
import com.PauloChaves.ProjetoCursoUdemy.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value= "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @GetMapping(value="{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id){
        Cliente obj = service.find(id);

        return ResponseEntity.ok().body(obj);
    }
}
