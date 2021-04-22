package com.PauloChaves.ProjetoCursoUdemy.resources;

import com.PauloChaves.ProjetoCursoUdemy.dto.CategoriaDTO;
import com.PauloChaves.ProjetoCursoUdemy.dto.ProdutoDTO;
import com.PauloChaves.ProjetoCursoUdemy.entities.Categoria;
import com.PauloChaves.ProjetoCursoUdemy.entities.Pedido;
import com.PauloChaves.ProjetoCursoUdemy.entities.Produto;
import com.PauloChaves.ProjetoCursoUdemy.resources.utils.URL;
import com.PauloChaves.ProjetoCursoUdemy.services.PedidoService;
import com.PauloChaves.ProjetoCursoUdemy.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService service;

    @GetMapping(value="{id}")
    public ResponseEntity<Produto> findById(@PathVariable Long id){
        Produto obj = service.find(id);

        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            @RequestParam(value = "nome",defaultValue = "0")String nome,
            @RequestParam(value = "categorias",defaultValue = "0")String categorias,
            @RequestParam(value = "page",defaultValue = "0")Integer page,
            @RequestParam (value = "linesPerPage",defaultValue = "24")Integer LinesPerPage,
            @RequestParam (value = "orderBy",defaultValue = "nome")String orderBy,
            @RequestParam (value = "direction",defaultValue = "ASC")String direction){
        String nomeDecoded = URL.decodeParam(nome);

        List<Long> ids = URL.decodeIntList(categorias);
        Page<Produto> list = service.search(nome,ids,page,LinesPerPage,orderBy,direction);
        Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));

        return ResponseEntity.ok().body(listDto);
    }
}
